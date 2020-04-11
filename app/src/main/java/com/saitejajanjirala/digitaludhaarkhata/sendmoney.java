package com.saitejajanjirala.digitaludhaarkhata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.Format;
import java.util.ArrayList;

public class sendmoney extends AppCompatActivity {
    TextInputEditText mamounttext;
    TextInputLayout mamountlayout;
    Button msavebutton;
    String muid,maccountname,mcustomername,mbutton;
    Customertotal mcustomertotal;
    ArrayList<Chatdata> chatdataarraylist;
    int amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmoney);
        amount=0;
        mbutton=getIntent().getStringExtra("button");
        muid=getIntent().getStringExtra("uid");
        maccountname=getIntent().getStringExtra("accountname");
        mcustomername=getIntent().getStringExtra("customername");
        msavebutton=findViewById(R.id.savemoney);
        chatdataarraylist=new ArrayList<>();
        mamountlayout=findViewById(R.id.amountlayout);
        mamounttext=findViewById(R.id.amounttext);
        mcustomertotal=new Customertotal();
        msavebutton.setEnabled(false);
        mamounttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()<=0){
                    mamountlayout.setError(getResources().getString(R.string.amountlengthlow));
                }
                else if(editable.length()>10){
                    mamountlayout.setError(getResources().getString(R.string.amountlengthhigh));
                }
                else{
                    msavebutton.setEnabled(true);
                    mamountlayout.setError(null);
                }
            }
        });
        msavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("customernamae",mcustomername);
                if(mamounttext.getText().toString().length()>0){
                    final int amount1=Integer.parseInt(mamounttext.getText().toString());
                    if(amount1!=0){
                       Dobackground dobackground=new Dobackground();
                       dobackground.execute(mamounttext.getText().toString());
                    }
                    else{
                        mamountlayout.setError(getResources().getString(R.string.amountlengthlow));
                    }
                }


            }
        });
    }
    public void updatedata(Customertotal customertotal,int amount2){
        mcustomertotal=customertotal;
        amount=amount2;
    }
    @SuppressLint("StaticFieldLeak")
    public class Dobackground extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... amountstr) {
            final int amount1=Integer.parseInt(amountstr[0]);
            final FirebaseFirestore db=FirebaseFirestore.getInstance();
            DocumentReference documentReference=db.document("users/"+muid+"/khataaccounts/"+maccountname+"/customeraccounts/"+mcustomername);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    Customertotal customertotal2=new Customertotal();
                    assert documentSnapshot != null;
                    customertotal2=documentSnapshot.toObject(Customertotal.class);
                    updatedata(customertotal2,amount1);
                    msavebutton.setVisibility(View.INVISIBLE);
                    Chatdata chatdata=new Chatdata();
                    if(mbutton.equals("myougot")){
                        chatdata.setMdate(new Myappdate().getMdate());
                        chatdata.setMyougot(amount);
                        chatdata.setMyougave(0);
                        int updatedamount=mcustomertotal.getMtotalgot()+amount;
                        mcustomertotal.setMtotalgot(updatedamount);
                        chatdataarraylist=mcustomertotal.getChatdata();
                        chatdataarraylist.add(chatdata);
                        mcustomertotal.setChatdata(chatdataarraylist);
                        FirebaseFirestore db1=FirebaseFirestore.getInstance();
                        final DocumentReference documentReference=db1.document("users/"+muid+"/khataaccounts/"+maccountname+"/customeraccounts/"+mcustomername);
                        documentReference.update("chatdata",mcustomertotal.getChatdata(),"mtotalgave",mcustomertotal.getMtotalgave(),"mtotalgot",mcustomertotal.getMtotalgot());
                    }
                    if(mbutton.equals("myougave")){
                        chatdata.setMdate(new Myappdate().getMdate());
                        chatdata.setMyougot(0);
                        chatdata.setMyougave(amount);
                        int updateamount=mcustomertotal.getMtotalgave()+amount;
                        mcustomertotal.setMtotalgave(updateamount);
                        chatdataarraylist=mcustomertotal.getChatdata();
                        chatdataarraylist.add(chatdata);
                        mcustomertotal.setChatdata(chatdataarraylist);
                        FirebaseFirestore db1=FirebaseFirestore.getInstance();
                        final DocumentReference documentReference=db1.document("users/"+muid+"/khataaccounts/"+maccountname+"/customeraccounts/"+mcustomername);
                        documentReference.update("chatdata",mcustomertotal.getChatdata(),"mtotalgave",mcustomertotal.getMtotalgave(),"mtotalgot",mcustomertotal.getMtotalgot());


                    }

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent=new Intent(sendmoney.this,UserContents.class);
            //intent.putExtra("accountname",maccountname);
            intent.putExtra("uid",muid);
            // intent.putExtra("customername",mcustomername);
            finish();
            startActivity(intent);
            msavebutton.setVisibility(View.VISIBLE);
        }
    }
}
