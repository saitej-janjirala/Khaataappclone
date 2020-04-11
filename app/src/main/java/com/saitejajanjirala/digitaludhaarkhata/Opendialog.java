package com.saitejajanjirala.digitaludhaarkhata;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.appcompat.app.AlertDialog;

public class Opendialog {
    Context mcontext;
    String mtitle;
    String mmessage;
    @Inject
    public Opendialog(@Named("dialog context")Context context,@Named("dialogtitle") String title,@Named("dialogmessage") String message){
        this.mcontext=context;
        this.mtitle=title;
        this.mmessage=message;

    }
    public void createdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(mcontext);
        builder.setMessage(mmessage) .setTitle(mtitle);

        //Setting message manually and performing action on button click

        AlertDialog alert = builder.create();
        alert.show();
    }
}
