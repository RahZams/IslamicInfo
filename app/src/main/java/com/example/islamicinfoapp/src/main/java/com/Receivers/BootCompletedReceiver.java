package com.example.islamicinfoapp.src.main.java.com.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.services.BootService;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import java.util.Map;

public class BootCompletedReceiver extends BroadcastReceiver {
//    Map<String,?> allSharedPrefs;
//    String mCityName,mCountryName;
    private static final String TAG = BootCompletedReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Constants.PRAYER_TAG, TAG + " onReceive: " + intent.getAction() +
                SharedPrefsHelper.getValue(context, Constants.FAJR));
        Intent serviceIntent = new Intent(context, BootService.class);
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"islamicapp:my_wake_lock");
        wakeLock.acquire();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Log.d(Constants.PRAYER_TAG,TAG + " onReceive: O" );
            context.startForegroundService(serviceIntent);
        }
        else{
            context.startService(serviceIntent);
        }

//        mCityName = SharedPrefsHelper.getValue(context,context.getString(R.string.cityname));
//        mCountryName = SharedPrefsHelper.getValue(context,context.getString(R.string.countryname));
//        allSharedPrefs = SharedPrefsHelper.getAllSharedPrefs(context);
//        Log.d("prayer", "onReceive: " + mCityName + " " + mCountryName);
//        Set<String> sharedPrefsKeys = allSharedPrefs.keySet();
//        Log.d("prayer", "onReceive: " + sharedPrefsKeys.size());
//        for (String s:sharedPrefsKeys) {
//            Log.d("prayer", "onReceive: s" + s);
//            if (SharedPrefsHelper.getValue(context, s).contains(",")) {
//                String[] sharedPrefsValues = SharedPrefsHelper.getValue(context, s).split(",");
//                Log.d("prayer", "onReceive: sharedPrefsValues" + sharedPrefsValues[0] + " : " + sharedPrefsValues[1] +
//                        ":" + sharedPrefsValues[2]);
//                if (sharedPrefsValues[2].equals("true")) {
//                    Log.d("prayer", "onReceive: " + "true");
//                    //if (sharedPrefsValues[0] > Utility.getCurrentTime())
////                Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(Utility.getCurrentDate())),
////                        sharedPrefsValues[1],
////                        Utility.createPendingIntent(context,s,sharedPrefsValues[1],mCityName,mCountryName));
//                    Log.d("prayer", "onReceive: " + sharedPrefsValues[0] + " " + sharedPrefsValues[1]);
//                    Utility.setupReminder(context, sharedPrefsValues[0],
//                            sharedPrefsValues[1],
//                            Utility.createPendingIntent(context, s, sharedPrefsValues[1], mCityName, mCountryName));
//                }
//          }
        }

//        for (String s:sharedPrefsKeys) {
//            Log.d("prayer", "onReceive: " + s);
//            Log.d("prayer", "onReceive: " + SharedPrefsHelper.getValue(context, s));
//            if (SharedPrefsHelper.getValue(context, s).contains("-")) {
//                Log.d("prayer", "onReceive: " + SharedPrefsHelper.getValue(context, s));
//            }
//        }

}
