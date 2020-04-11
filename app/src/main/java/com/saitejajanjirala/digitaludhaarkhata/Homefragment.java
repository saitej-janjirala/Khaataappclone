package com.saitejajanjirala.digitaludhaarkhata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Homefragment extends Fragment {
    RecyclerView mrecyclerview;
    RecyclerView.LayoutManager mlayoutManager;
    Button xaddcustomersbutton;
    ProgressBar mprogressbar;
    customercontentsadpater madapter;
    String maccountname;
    int mgetamounttoatl,mgiveamounttotal;
    ArrayList<Customertotal> mcustomertotalArrayList;
    String muid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.homefragment,container,false);
        xaddcustomersbutton=view.findViewById(R.id.maddcustomersbutton);
        xaddcustomersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager= Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new addcontact(maccountname,muid));
                fragmentTransaction.commit();
            }
        });
        mrecyclerview=view.findViewById(R.id.homerecyclerview);
        mprogressbar=view.findViewById(R.id.progressbar);
        mprogressbar.setVisibility(View.VISIBLE);
        mlayoutManager=new LinearLayoutManager(getContext());
        mcustomertotalArrayList=new ArrayList<>();
        getdata();
        mprogressbar.setVisibility(View.GONE);

        return view;
    }
    public Homefragment(String acountname,String uid){
        maccountname=acountname;
        muid=uid;
    }

    public void getdata(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Udhaarkhata",Context.MODE_PRIVATE);
        String xaccountname=sharedPreferences.getString("selectedaccount","");
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        try {
            CollectionReference collectionReference=db.collection("users/"+muid+"/khataaccounts/"+xaccountname+"/customeraccounts");
            collectionReference.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<Customertotal> totalArrayList2=new ArrayList<>();
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot queryDocumentSnapshot: Objects.requireNonNull(task.getResult())){
                                    totalArrayList2.add(queryDocumentSnapshot.toObject(Customertotal.class));
                                }
                            }
                            setdata(totalArrayList2);
                        }
                    });
        }
        catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }
    public void setdata(ArrayList<Customertotal> customertotalslist){
        mcustomertotalArrayList=customertotalslist;
        int youwillgetsum=0,youwillgivesum=0;
        /*
        for(int i=0;i<mcustomertotalArrayList.size();i++){
            Log.d("names",mcustomertotalArrayList.get(i).mid);
            Log.d("names",mcustomertotalArrayList.get(i).getMphonenumber());
            Log.d("names",mcustomertotalArrayList.get(i).getMtotalgave()+"");
            Log.d("names",mcustomertotalArrayList.get(i).getMtotalgot()+"");

        }
         */
        mrecyclerview.setHasFixedSize(true);
        madapter=new customercontentsadpater(mcustomertotalArrayList);
        mrecyclerview.setLayoutManager(mlayoutManager);
        mrecyclerview.setAdapter(madapter);
        mprogressbar.setVisibility(View.GONE);
        madapter.oncustomernameclicked(new customercontentsadpater.oncustomernameclicked() {
            @Override
            public void onclicked(int position) {
                Intent intent=new Intent(getActivity(),Customerchat.class);
                intent.putExtra("accountname",maccountname);
                intent.putExtra("uid",muid);
                intent.putExtra("customername",mcustomertotalArrayList.get(position).getMid());
                startActivity(intent);

            }
        });
    }

    }

