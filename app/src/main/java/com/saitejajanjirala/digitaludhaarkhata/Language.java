package com.saitejajanjirala.digitaludhaarkhata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Language extends AppCompatActivity {

    CardView englishcard,urducard,hindicard,maraticard,gujaraticard,punjabicard,telugucard,tamilcard,malayalamcard,kannadacard,bengalicar,sindhicard,nepalicard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        //Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_LONG).show();
        englishcard=findViewById(R.id.englishcard);
        urducard=findViewById(R.id.urducard);
        hindicard=findViewById(R.id.hindicard);
        maraticard=findViewById(R.id.maraticard);
        gujaraticard=findViewById(R.id.gujaraticard);
        punjabicard=findViewById(R.id.punjabicard);
        telugucard=findViewById(R.id.telugucard);
        tamilcard=findViewById(R.id.tamilcard);
        malayalamcard=findViewById(R.id.malayalamcard);
        kannadacard=findViewById(R.id.kannadacard);


    }

    public void languageclicked(View view) {
        if(view.getId()==R.id.englishcard){
            Intent intent=new Intent(Language.this,Enterphonenumber.class);
            startActivity(intent);

        }
        else if(view.getId()==R.id.urducard){

        }
        else if(view.getId()==R.id.hindicard){

        }
        else if(view.getId()==R.id.maraticard){

        }
        else if(view.getId()==R.id.gujaraticard){

        }
        else if(view.getId()==R.id.punjabicard){

        }
        else if(view.getId()==R.id.telugucard){

        }
        else if(view.getId()==R.id.tamilcard){

        }
        else if(view.getId()==R.id.malayalamcard){

        }
        else if(view.getId()==R.id.kannadacard){

        }
        else if(view.getId()==R.id.bengalicard){

        }
        else if(view.getId()==R.id.sindhicard){

        }
        else if(view.getId()==R.id.nepalicard){

        }



    }
}
