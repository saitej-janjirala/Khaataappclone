package com.saitejajanjirala.digitaludhaarkhata;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class addcontact extends Fragment {
    TextInputLayout mcustomernumberlayout,mcustomernamelayout;
    TextInputEditText mcustomernumbertext,mcustomernametext;
    Button mbutton;
    String muid,maccountname;
    ProgressBar mprogressbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.addcontact,container,false);
        mcustomernamelayout=v.findViewById(R.id.customernamelayout);
        mcustomernumberlayout=v.findViewById(R.id.customernumberlayout);
        mcustomernumbertext=v.findViewById(R.id.customernumbertext);
        mcustomernametext=v.findViewById(R.id.customernametext);
        mbutton=v.findViewById(R.id.addcustommerbutton);
        mprogressbar=v.findViewById(R.id.progressbar);
        mcustomernametext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>30 ){
                    mcustomernamelayout.setError(getResources().getString(R.string.customernamehigh));
                }
                else if(editable.length()<1){
                    mcustomernamelayout.setError(getResources().getString(R.string.customernamelow));
                }
                else{
                    mcustomernamelayout.setError(null);
                }

            }
        });
        mcustomernumbertext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>10 || editable.length()<10){
                    mcustomernumberlayout.setError(getResources().getString(R.string.phonenumberlengtherror));
                }
                else{
                    mcustomernumberlayout.setError(null);
                }
            }
        });
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=mcustomernumbertext.getText().toString();
                final String name=mcustomernametext.getText().toString();
                if(number.length()==10 && 1<name.length() && name.length()<30){
                    mbutton.setEnabled(false);
                    final FirebaseFirestore db=FirebaseFirestore.getInstance();
                    final Chatdata chatdata=new Chatdata();
                    final ArrayList<Chatdata> listchatdata=new ArrayList<>();
                    final Customertotal total=new Customertotal();
                    total.setMid(name);
                    total.setMphonenumber(number);
                    total.setMtotalgave(0);
                    total.setMtotalgot(0);
                    chatdata.setMdate(new Myappdate().getMdate());
                    chatdata.setMyougave(0);
                    chatdata.setMyougot(0);
                    listchatdata.add(chatdata);
                    total.setChatdata(listchatdata);
                   // final HashMap<String,ArrayList<Chatdata>> map=new HashMap<>();
                   // map.put("chatdata",listchatdata);
                    final DocumentReference documentReference=db.document("users/"+muid+"/khataaccounts/"+maccountname+"/customeraccounts/"+name);
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if(documentSnapshot.exists()){
                                Toast.makeText(getContext(),"already exists",Toast.LENGTH_LONG).show();
                                mbutton.setEnabled(true);
                                mprogressbar.setVisibility(View.GONE);
                            }
                            else{
                                documentReference.set(total)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mbutton.setEnabled(true);
                                                mprogressbar.setVisibility(View.GONE);
                                                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.container,new Homefragment(maccountname,muid));
                                                fragmentTransaction.commit();
                                            }
                                        });
                            }
                        }
                    });

                }
                else{
                        mbutton.setEnabled(true);
                        mprogressbar.setVisibility(View.GONE);
                }
            }
        });
        return v;
        //return inflater.inflate(R.layout.addcontact,container,false);
    }
    public void setchatdata(){

    }
    public addcontact(String accountname,String uid){
        maccountname=accountname;
        muid=uid;

    }
}
