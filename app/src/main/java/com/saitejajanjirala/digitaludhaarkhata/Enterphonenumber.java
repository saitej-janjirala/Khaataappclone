package com.saitejajanjirala.digitaludhaarkhata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cunoraz.gifview.library.GifView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneMultiFactorAssertion;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;

import javax.inject.Inject;

import static android.Manifest.permission.RECEIVE_SMS;
import static java.security.AccessController.getContext;

public class Enterphonenumber extends AppCompatActivity {

    TextInputEditText numbertext;
    TextInputLayout numberlayout;
    Button getotp;
    Check check;
    @Inject Checkconnection checkconnection;
    @Inject Opendialog opendialog;
    @Inject MyFirebase myFirebase;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterphonenumber);
        GifView gifView1 = findViewById(R.id.gif1);
        gifView1.setVisibility(View.VISIBLE);
        gifView1.play();
        gifView1.setGifResource(R.drawable.dialnumber);
        gifView1.getGifResource();
        numberlayout=findViewById(R.id.numberlayout);
        numbertext=findViewById(R.id.numbertext);
        getotp=findViewById(R.id.getotp);
        try{
            check=DaggerCheck.builder().context(Enterphonenumber.this)
                    .dialogcontext(Enterphonenumber.this)
                    .dialogmessage(getResources().getString(R.string.networkerro_message))
                    .dialogtitle(getResources().getString(R.string.networkerror_title))
                    .userid("")
                    .build();
            check.inject(Enterphonenumber.this);
        }
        catch(Exception e){
            Toast.makeText(Enterphonenumber.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
        numbertext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                    if(editable.length()>10 || editable.length()<10){
                        numberlayout.setError(getResources().getString(R.string.phonenumberlengtherror));
                    }
                    else{
                        numberlayout.setError(null);
                    }
            }
        });
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=numbertext.getText().toString();
                if(number.length()!=10){
                    numberlayout.setError(getResources().getString(R.string.phonenumberlengtherror));
                }
                else{

                    try {
                        //Toast.makeText(this,""+checkconnection.isconnectionavailable(),Toast.LENGTH_LONG).show();
                        if(checkconnection.isconnectionavailable()){
                            Intent intent=new Intent(Enterphonenumber.this,Otp.class);
                            intent.putExtra("phonenumber",number);
                            startActivity(intent);
                        }
                        else{
                            opendialog.createdialog();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(Enterphonenumber.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }








}
