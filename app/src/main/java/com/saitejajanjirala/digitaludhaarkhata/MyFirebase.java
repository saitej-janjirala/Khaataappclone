package com.saitejajanjirala.digitaludhaarkhata;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.NoAllocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;

public class MyFirebase {
    private String muid;
    FirebaseFirestore db;
     boolean created;
     boolean opened;
    @Inject
    public MyFirebase(@Named("muid") String uid){
        this.muid=uid;
        db=FirebaseFirestore.getInstance();
    }
    public void checkifkhataaccountopened(){
        CollectionReference collectionReference=db.collection("users/"+muid+"/khataaccounts");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d("accountresults", Objects.requireNonNull(task.getResult()).isEmpty()+"");
                            if(!task.getResult().isEmpty()){

                            }else {

                            }
                        }
                    }
                });

    }



}
