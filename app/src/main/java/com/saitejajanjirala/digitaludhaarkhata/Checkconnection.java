package com.saitejajanjirala.digitaludhaarkhata;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

import java.net.NetworkInterface;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;

public class Checkconnection {
    private Context mcontext;
    @Inject
    public Checkconnection(@Named("enternumber context") Context context){
        this.mcontext=context;
    }
    public boolean isconnectionavailable(){
        ConnectivityManager cm = (ConnectivityManager)           mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            return true;
        }
        else {
            return false;
        }

    }


}
