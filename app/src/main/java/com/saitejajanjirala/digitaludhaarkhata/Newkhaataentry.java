package com.saitejajanjirala.digitaludhaarkhata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;

import java.util.HashMap;

import javax.inject.Inject;

public class Newkhaataentry extends AppCompatActivity {

    TextInputEditText newkhatatext;
    TextInputLayout newkhatalayout;
    Button createkhatabutton;
    ProgressBar mprogressbar;
    String muid;
    Check check;
    boolean created;
    @Inject Checkconnection checkconnection;
    @Inject Opendialog opendialog;
    @Inject MyFirebase myFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newkhaataentry);
        muid=getIntent().getStringExtra("uid");
        newkhatatext=findViewById(R.id.newkhatatext);
        newkhatalayout=findViewById(R.id.newkhatalayout);
        createkhatabutton=findViewById(R.id.createkhata);
        mprogressbar=findViewById(R.id.progressbar);
        newkhatatext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()<5){
                    newkhatalayout.setError(getResources().getString(R.string.khatanamelowerror));
                }
                else if(editable.length()>25){
                    newkhatalayout.setError(getResources().getString(R.string.khatanamehigherror));
                }
                else {
                    newkhatalayout.setError(null);
                }

            }
        });
        createkhatabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String khataname=newkhatatext.getText().toString();
                createkhatabutton.setEnabled(false);
                if(khataname.isEmpty()|| khataname.length()<5) {
                    newkhatalayout.setError(getResources().getString(R.string.khatanamelowerror));
                    createkhatabutton.setEnabled(true);
                }
                else if(khataname.length()>25){
                    newkhatalayout.setError(getResources().getString(R.string.khatanamehigherror));
                    createkhatabutton.setEnabled(true);
                }
                else {
                    check=DaggerCheck.builder().context(Newkhaataentry.this)
                            .dialogcontext(Newkhaataentry.this)
                            .dialogmessage(getResources().getString(R.string.networkerro_message))
                            .dialogtitle(getResources().getString(R.string.networkerror_title))
                            .userid("")
                            .build();
                    check.injection2(Newkhaataentry.this);
                    if(checkconnection.isconnectionavailable()){
                        try{
                            createnewkhata(khataname);
                        }
                        catch (Exception e){
                            Toast.makeText(Newkhaataentry.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                                  opendialog.createdialog();
                    }
                }
        }

    }); }
    public void createnewkhata(String maccountname){
        SharedPreferences sharedPreferences=getSharedPreferences("Udhaarkhata",MODE_PRIVATE);
        muid=sharedPreferences.getString("uid","");
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        final Total total=new Total();
        total.setMid(maccountname);
        total.setMyouwillget(0);
        total.setMyouwillgive(0);
        final DocumentReference reference=db.document("users/"+muid+"/khataaccounts/"+maccountname);
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                if(documentSnapshot.exists()){
                    Toast.makeText(Newkhaataentry.this,"account name already exists",Toast.LENGTH_LONG).show();
                }
                else {
                    reference.set(total)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    HashMap<String,Boolean> map=new HashMap<>();
                                    map.put("newaccountcreated",true);
                                    FirebaseFirestore db1=FirebaseFirestore.getInstance();
                                    db1.collection("users").document(muid).set(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent intent = new Intent(Newkhaataentry.this, UserContents.class);
                                                    intent.putExtra("uid",muid);
                                                    Toast.makeText(Newkhaataentry.this,"account created",Toast.LENGTH_LONG).show();
                                                    startActivity(intent);
                                                }
                                            });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Newkhaataentry.this,e.getMessage(),Toast.LENGTH_LONG).show(); }
                            });
                }
            }
        });

}}
