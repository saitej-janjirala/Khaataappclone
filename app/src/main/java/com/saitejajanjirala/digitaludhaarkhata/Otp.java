package com.saitejajanjirala.digitaludhaarkhata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;


public class Otp extends AppCompatActivity{
    private Button validateButton;
    private String verificationid,phonenumber;
    private FirebaseAuth mauth;
    TextInputEditText otptext;
    TextInputLayout otplayout;
    ProgressBar mprogressbar;
    @Inject Checkconnection checkconnection;
    @Inject Opendialog opendialog;
    @Inject MyFirebase myFirebase;
    Check check;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Intent intent=getIntent();
        phonenumber=intent.getStringExtra("phonenumber");
        validateButton = findViewById(R.id.validate_button);
        mprogressbar=findViewById(R.id.progressbar);
        otplayout=findViewById(R.id.otplayout);
        otptext=findViewById(R.id.otptext);
        mauth=FirebaseAuth.getInstance();
        sendotp(phonenumber);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String code=otptext.getText().toString();
            if(code.length()!=6){
                otplayout.setError(getResources().getString(R.string.otplengtherror));
            }else {
                verifyVerificationCode(code);
            }

            }
        });
        otptext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()!=6){
                    otplayout.setError(getResources().getString(R.string.otplengtherror));
                }
                else {
                    otplayout.setError(null);
                }
            }
        });



    }
    public void sendotp(String phonenumber){
        final String x="+91"+phonenumber;
        try {
            PhoneAuthProvider phoneAuthProvider=PhoneAuthProvider.getInstance();
            phoneAuthProvider.verifyPhoneNumber(
                    x, 60, TimeUnit.SECONDS, Otp.this
                    , new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code=phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        otptext.setText(code);
                        validateButton.setEnabled(false);
                        verifyVerificationCode(code);

                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                   // super.onCodeSent(s, forceResendingToken);
                    Toast.makeText(Otp.this,"code sent to"+x,Toast.LENGTH_LONG).show();
                    mprogressbar.setVisibility(View.VISIBLE);
                    verificationid=s;
                }
            });
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

    }
    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        //signing the user
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
       mauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   //Toast.makeText(Otp.this,)
                   mprogressbar.setVisibility(View.GONE);
                   SharedPreferences sharedPreferences=getSharedPreferences("Udhaarkhata",MODE_PRIVATE);
                   SharedPreferences.Editor editor=sharedPreferences.edit();
                   editor.putString("uid",mauth.getUid());
                   editor.apply();
                   check=DaggerCheck.builder().dialogmessage(getResources().getString(R.string.networkerro_message))
                           .dialogtitle(getResources().getString(R.string.networkerror_title))
                           .dialogcontext(Otp.this)
                           .context(Otp.this)
                           .userid(mauth.getUid())
                           .build();
                   check.injection1(Otp.this);
                   try {
                       checkifkhataaccountopened();
                        boolean accountopened=sharedPreferences.contains("accountopened");
                       if(accountopened){

                       }
                       else{

                       }
                   }
                   catch (Exception e){
                       Log.d("error",e.getMessage());
                       Toast.makeText(Otp.this,e.getMessage(),Toast.LENGTH_LONG).show();
                   }

               }
               else{
                   Toast.makeText(Otp.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                   validateButton.setEnabled(true);
               }
           }
       });

    }
    public void checkifkhataaccountopened(){
        SharedPreferences sharedPreferences=getSharedPreferences("Udhaarkhata",MODE_PRIVATE);
        final String useruid=sharedPreferences.getString("uid","");
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("users").document(useruid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot.exists()){
                            Intent intent=new Intent(Otp.this,UserContents.class);
                            intent.putExtra("uid",useruid);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Intent intent=new Intent(Otp.this,Newkhaataentry.class);
                            intent.putExtra("uid",useruid);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


    }

        //return false;
    }



