package com.saitejajanjirala.digitaludhaarkhata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Customerchat extends AppCompatActivity {
    TextView mshowcustomername;
    RecyclerView mrecyclerview;
    CardView mgavecard,mgotcard;
    customerchatadapter madapter;
    RecyclerView.LayoutManager mlayoutmanager;
    String maccountname,muid,mcustomername;
    boolean gaveenabled=false,gotenabled=false;
    ImageView gobackimageview;
    Customertotal mcustomertotal;
    ArrayList<Chatdata> mchatlist;
    Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerchat);
        maccountname=getIntent().getStringExtra("accountname");
        muid=getIntent().getStringExtra("uid");
        mcustomername=getIntent().getStringExtra("customername");
        mtoolbar=findViewById(R.id.customerchattoolbar);
        setSupportActionBar(mtoolbar);
        mtoolbar.setTitle(null);
        mtoolbar.setVisibility(View.VISIBLE);
        mchatlist=new ArrayList<>();
        mshowcustomername=findViewById(R.id.showcustomername);
        mrecyclerview=findViewById(R.id.customerchatrecyclerview);
        mgavecard=findViewById(R.id.yougavecard);
        mgotcard=findViewById(R.id.yougotcard);
        mcustomertotal=new Customertotal();
        mshowcustomername.setText(mcustomername);
        mlayoutmanager=new LinearLayoutManager(Customerchat.this);
        getdata();
        mgavecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Customerchat.this,sendmoney.class);
                intent.putExtra("accountname",maccountname);
                intent.putExtra("uid",muid);
                intent.putExtra("customername",mcustomername);
                intent.putExtra("button","myougave");
                startActivity(intent);
            }
        });
        mgotcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Customerchat.this,sendmoney.class);
                intent.putExtra("accountname",maccountname);
                intent.putExtra("uid",muid);
                intent.putExtra("customername",mcustomername);
                intent.putExtra("button","myougot");
                startActivity(intent);
            }
        });
        getdata();

    }
    public void getdata(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        final DocumentReference documentReference=db.document("users/"+muid+"/khataaccounts/"+maccountname+"/customeraccounts/"+mcustomername);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Customertotal customertotal2=new Customertotal();
                assert documentSnapshot != null;
                customertotal2=documentSnapshot.toObject(Customertotal.class);
                setdata(customertotal2);
            }
        });
    }
    public void setdata(Customertotal customertotal){
        mcustomertotal=customertotal;
        mrecyclerview.setHasFixedSize(true);
        for(int i=0;i<mchatlist.size();i++){
            Log.d("print date",mchatlist.get(i).getMdate());
            Log.d("yougave",mchatlist.get(i).getMyougave()+"");
            Log.d("yougot",mchatlist.get(i).getMyougot()+"");
        }
       madapter=new customerchatadapter(mchatlist);
        mrecyclerview.setLayoutManager(mlayoutmanager);
        mrecyclerview.setAdapter(madapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Customerchat.this, UserContents.class);
        intent.putExtra("uid",muid);
        startActivity(intent);
    }
}
