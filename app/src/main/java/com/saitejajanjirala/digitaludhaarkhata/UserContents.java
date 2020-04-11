package com.saitejajanjirala.digitaludhaarkhata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

public class UserContents extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    ProgressBar mprogressbar;
    String uid;
    Spinner spinner;
    ArrayAdapter arrayAdapter;
    Cursor sqlcursor;
    ArrayList<Total> totalArrayList;
    ArrayList<String> ids;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contents);
        totalArrayList=new ArrayList<>();
        ids=new ArrayList<>();
        uid = getIntent().getStringExtra("uid");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinner = findViewById(R.id.accountspinner);
        toolbar.setTitle("");
        mprogressbar = findViewById(R.id.progressbar);
        mprogressbar.setVisibility(View.VISIBLE);
        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        getaccountids();
        sharedPreferences=getSharedPreferences("Udhaarkhata",MODE_PRIVATE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                openfragment(new Homefragment(sharedPreferences.getString("selectedaccount",""),uid));
                                Toast.makeText(UserContents.this, "home clicked", Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.navigation_contents:
                                openfragment(new MoreFragment());
                                Toast.makeText(UserContents.this, "apps clicked", Toast.LENGTH_LONG).show();
                                return true;
                        }
                        return false;
                    }
                };
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences=getSharedPreferences("Udhaarkhata",MODE_PRIVATE);
        if(sharedPreferences.contains("selectedaccount") && !sharedPreferences.getString("selectedaccount","").isEmpty()){
            String name = sharedPreferences.getString("selectedaccount", "");
            spinner.setSelection(ids.indexOf(name));
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
        else{
            spinner.setSelection(0);
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }

    public void openfragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment,null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getaccountids() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firebaseFirestore.collection("users/" + uid + "/khataaccounts");
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Total> totalArrayList2=new ArrayList<>();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot: Objects.requireNonNull(task.getResult())){
                                totalArrayList2.add(queryDocumentSnapshot.toObject(Total.class));
                            }
                        }
                        setdropdown(totalArrayList2);
                    }
                });
    }
    void setdropdown(ArrayList<Total> totals){
        totalArrayList=totals;
        for(int i=0;i<totalArrayList.size();i++){
            ids.add(totalArrayList.get(i).getMid());
        }
        ids.add("create new account + ");
        arrayAdapter=new ArrayAdapter<String>(UserContents.this, R.layout.coloredspinner, ids);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==ids.size()-1){
                    spinner.setSelection(ids.indexOf(sharedPreferences.getString("selectedaccount","")));
                    Intent intent=new Intent(UserContents.this,Newkhaataentry.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                }
                else{
                    SharedPreferences sharedPreferences=getSharedPreferences("Udhaarkhata",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("selectedaccount",ids.get(i));
                    editor.apply();
                    openfragment(new Homefragment(ids.get(i),uid));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mprogressbar.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
