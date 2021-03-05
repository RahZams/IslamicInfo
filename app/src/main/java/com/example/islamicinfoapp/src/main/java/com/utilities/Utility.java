package com.example.islamicinfoapp.src.main.java.com.utilities;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.util.Log;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.view.LocationActivity;

import java.text.ParseException;
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

    public static String changeDateFormat(String timeData) {
        Date dateTime;
        String finalTime = "";
        try {
            if (timeData.endsWith("AM") || timeData.endsWith("PM") || timeData.endsWith("am") || timeData.endsWith("pm")){
                Log.d("prayer", "changeDateFormat: " + "if am/pm");
                dateTime = new SimpleDateFormat("hh:mm a").parse(timeData);
                finalTime = new SimpleDateFormat("hh:mm").format(dateTime);
            }
            else if (!timeData.startsWith("12")) {
                Log.d("prayer", "changeDateFormat: if");
                dateTime = new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(timeData);
                finalTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateTime);
            }
            else if (timeData.startsWith("12")){
                Log.d("prayer", "changeDateFormat: else");
                finalTime = timeData + " " + "PM";
            }
            Log.d("prayer", "changeDateFormat: " + finalTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            DateTimeFormatter parser = DateTimeFormatter.ofPattern("hh:mm",Locale.ENGLISH);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm aa",Locale.ENGLISH);
//            LocalTime time = LocalTime.parse(timeData,parser);
//            finalTime = time.format(formatter);
//        }
        return finalTime;
    }
}
