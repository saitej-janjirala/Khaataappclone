package com.saitejajanjirala.digitaludhaarkhata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject Checkconnection checkconnection;
    @Inject Opendialog opendialog;
    @Inject MyFirebase myFirebase;
    Check check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    SharedPreferences sharedPreferences=getSharedPreferences("Udhaarkhata",MODE_PRIVATE);
                    //SharedPreferences.Editor editor=sharedPreferences.edit();
                    String uid=sharedPreferences.getString("uid","");
                    if(uid.isEmpty()){
                        Intent intent =new Intent(MainActivity.this,Language.class);
                        startActivity(intent);
                        finish();
                    }
                    else{

                        check=DaggerCheck.builder().dialogmessage(getResources().getString(R.string.networkerro_message))
                                .dialogtitle(getResources().getString(R.string.networkerror_title))
                                .dialogcontext(MainActivity.this)
                                .context(MainActivity.this)
                                .userid(uid)
                                .build();
                        check.injection(MainActivity.this);
                        if(checkconnection.isconnectionavailable()){
                            checkifkhataaccountopened(uid);

                        }
                        else {
                            opendialog.createdialog();
                        }

                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        }, 1500);
    }
    public void checkifkhataaccountopened(final String muid){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("users").document(muid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        try{
                            if(documentSnapshot.getBoolean("newaccountcreated")){
                                Intent intent=new Intent(MainActivity.this,UserContents.class);
                                intent.putExtra("uid",muid);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intent=new Intent(MainActivity.this,Newkhaataentry.class);
                                intent.putExtra("uid",muid);
                                startActivity(intent);
                                finish();
                            }
                        }
                        catch (Exception ex){

                                Intent intent=new Intent(MainActivity.this,Newkhaataentry.class);
                                intent.putExtra("uid",muid);
                                startActivity(intent);
                                finish();

                        }


                    }
                });

    }
}
