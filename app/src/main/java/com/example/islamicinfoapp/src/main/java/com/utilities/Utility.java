package com.example.islamicinfoapp.src.main.java.com.utilities;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.util.Log;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.view.LocationActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {
    private ConnectivityManager mConnectivityManager;

    public static String getCurrentDate() {
        //Log.d("date", "getCurrentDate: " + Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat sf = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        Log.d("prayer", "getCurrentDate: " + Locale.getDefault());
        return sf.format(new Date());
    }

    public boolean checkForNetworkAvailibility(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mConnectivityManager.getActiveNetworkInfo()!= null && mConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void redirectingToProvideConnection(Context context, int ic_drawable) {
        Intent intent = null;
        if (ic_drawable == R.drawable.ic_no_network){
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        }
        else if (ic_drawable == R.drawable.ic_location_off){
            intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        }

        context.startActivity(intent);
    }

    public boolean checkForLocationConnection(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        if(!(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) && !(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))){
            return false;
        }
        else {
            return true;
        }

    }
}
