package com.example.islamicinfoapp.src.main.java.com.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.services.BootService;
import com.example.islamicinfoapp.src.main.java.com.services.ReminderLifeService;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;

import java.util.Map;
import java.util.Set;

import okhttp3.internal.Util;

public class BootCompletedReceiver extends BroadcastReceiver {
    Map<String,?> allSharedPrefs;
    String mCityName,mCountryName;
    private static final String TAG = BootCompletedReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Constants.PRAYER_TAG, TAG + " onReceive: " + intent.getAction() +
                SharedPrefsHelper.getValue(context, Constants.FAJR));
        scheduleAlarm(context);
        //Intent serviceIntent = new Intent(context, BootService.class);
        //startWakefulService(context, serviceIntent);
    }

    private void scheduleAlarm(Context context) {
        mCityName = SharedPrefsHelper.getValue(context,context.getString(R.string.cityname));
        mCountryName = SharedPrefsHelper.getValue(context,context.getString(R.string.countryname));
        Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + mCityName + " " + mCountryName);
        allSharedPrefs = SharedPrefsHelper.getAllSharedPrefs(context);
        Set<String> sharedPrefsKeys = allSharedPrefs.keySet();
        Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: " + sharedPrefsKeys.size());
        for (String s:sharedPrefsKeys){
            Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: s" + s);
            if (SharedPrefsHelper.getValue(context,s).contains(",")){
                String[] sharedPrefsValues = SharedPrefsHelper.getValue(context,s).split(",");
                Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: sharedPrefsValues" + sharedPrefsValues[0]
                        + " : " + sharedPrefsValues[1] + ":" + sharedPrefsValues[2]);
                if (Utility.compareTwoTimings(sharedPrefsValues[1],Utility.getSystemTime()) &&
                        sharedPrefsValues[2].equals("true")){
                    Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: " + "true");
                    Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: " + sharedPrefsValues[0] + " " + sharedPrefsValues[1]);
                    Utility.setupReminder(context,sharedPrefsValues[0],sharedPrefsValues[1],
                            Utility.createPendingIntent(context,s,sharedPrefsValues[1],mCityName,mCountryName));
                }
            }
        }
    }
}
