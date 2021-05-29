package com.example.islamicinfoapp.src.main.java.com.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;

import java.util.Map;
import java.util.Set;

public class BootService extends Service {
    String mCityName,mCountryName;
    Map<String,?> allSharedPrefs;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("prayer", "BootService:onStartCommand: " + intent);
        mCityName = SharedPrefsHelper.getValue(getApplicationContext(),
                getApplicationContext().getString(R.string.cityname));
        mCountryName = SharedPrefsHelper.getValue(getApplicationContext(),
                getApplicationContext().getString(R.string.countryname));
        allSharedPrefs = SharedPrefsHelper.getAllSharedPrefs(getApplicationContext());
        Log.d("prayer", "BootService:onStartCommand: " + mCityName + " " + mCountryName);
        Set<String> sharedPrefsKeys = allSharedPrefs.keySet();
        Log.d("prayer", "BootService onStartCommand: " + sharedPrefsKeys.size());
        for (String s:sharedPrefsKeys) {
            Log.d("prayer", "BootService onStartCommand: s" + s);
            if (SharedPrefsHelper.getValue(getApplicationContext(), s).contains(",")) {
                String[] sharedPrefsValues = SharedPrefsHelper.getValue(getApplicationContext(), s).
                        split(",");
                Log.d("prayer", "BootService onStartCommand: sharedPrefsValues" + sharedPrefsValues[0]
                        + " : " + sharedPrefsValues[1] + ":" + sharedPrefsValues[2]);
                if (sharedPrefsValues[2].equals("true")) {
                    Log.d("prayer", "BootService onStartCommand: " + "true");
                    //if (sharedPrefsValues[0] > Utility.getCurrentTime())
//                Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(Utility.getCurrentDate())),
//                        sharedPrefsValues[1],
//                        Utility.createPendingIntent(context,s,sharedPrefsValues[1],mCityName,mCountryName));
                    Log.d("prayer", "BootService onStartCommand: " + sharedPrefsValues[0] + " " + sharedPrefsValues[1]);
                    Utility.setupReminder(getApplicationContext(), sharedPrefsValues[0],
                            sharedPrefsValues[1],
                            Utility.createPendingIntent(getApplicationContext(), s,
                                    sharedPrefsValues[1], mCityName, mCountryName));
                }
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("prayer", "BootService : onCreate:");
    }
}
