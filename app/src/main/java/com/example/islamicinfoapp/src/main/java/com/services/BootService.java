package com.example.islamicinfoapp.src.main.java.com.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.Receivers.BootCompletedReceiver;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;
import java.util.Map;
import java.util.Set;

public class BootService extends Service {

    String mCityName,mCountryName;
    Map<String,?> allSharedPrefs;
    private static final String TAG = BootService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.PRAYER_TAG,TAG +  " onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.PRAYER_TAG, TAG + " onStartCommand: " + intent);
        mCityName = SharedPrefsHelper.getValue(getApplicationContext(),
                getApplicationContext().getString(R.string.cityname));
        mCountryName = SharedPrefsHelper.getValue(getApplicationContext(),
                getApplicationContext().getString(R.string.countryname));
        allSharedPrefs = SharedPrefsHelper.getAllSharedPrefs(getApplicationContext());
        Log.d(Constants.PRAYER_TAG, TAG + " onStartCommand: " + mCityName + " " + mCountryName);
        Set<String> sharedPrefsKeys = allSharedPrefs.keySet();
        Log.d(Constants.PRAYER_TAG, TAG + " onStartCommand: " + sharedPrefsKeys.size());
        for (String s:sharedPrefsKeys) {
            Log.d(Constants.PRAYER_TAG, TAG + " onStartCommand: s" + s);
            if (SharedPrefsHelper.getValue(getApplicationContext(), s).contains(",")) {
                String[] sharedPrefsValues = SharedPrefsHelper.getValue(getApplicationContext(), s).
                        split(",");
                Log.d(Constants.PRAYER_TAG, TAG + " onStartCommand: sharedPrefsValues" + sharedPrefsValues[0]
                        + " : " + sharedPrefsValues[1] + ":" + sharedPrefsValues[2]);
                if (Utility.compareTwoTimings(sharedPrefsValues[1],Utility.getSystemTime()) && sharedPrefsValues[2].equals("true")) {
                    Log.d(Constants.PRAYER_TAG, TAG + " onStartCommand: " + "true");
                    //if (sharedPrefsValues[0] > Utility.getCurrentTime())
//                Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(Utility.getCurrentDate())),
//                        sharedPrefsValues[1],
//                        Utility.createPendingIntent(context,s,sharedPrefsValues[1],mCityName,mCountryName));
                    Log.d(Constants.PRAYER_TAG, TAG + " onStartCommand: " + sharedPrefsValues[0] + " " + sharedPrefsValues[1]);
                    Utility.setupReminder(BootService.this, sharedPrefsValues[0],
                            sharedPrefsValues[1],
                            Utility.createPendingIntent(BootService.this, s,
                                    sharedPrefsValues[1], mCityName, mCountryName));
                }
            }
        }
        //BootCompletedReceiver.completeWakefulIntent(intent);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.d("prayer", "BootSe434Drvice : onCreate:");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            startForeground(NOTIFICATION_ID,new Notification.Builder(this).build());
//        }
//    }
}
