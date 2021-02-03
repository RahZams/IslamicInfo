package com.example.islamicinfoapp.src.main.java.com.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.view.LocationActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    private ConnectivityManager mConnectivityManager;

    public static String getCurrentDate() {
        //Log.d("date", "getCurrentDate: " + Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat sf = new SimpleDateFormat("dd MMM yyyy");
        return sf.format(new Date());
    }

    public boolean checkForNetworkAvailibility(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mConnectivityManager.getActiveNetworkInfo()!= null && mConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void redirectingToProvideConnection(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        context.startActivity(intent);
    }
}
