package com.example.islamicinfoapp.src.main.java.com.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;

import java.util.Map;
import java.util.Set;

public class BootCompletedReceiver extends BroadcastReceiver {
    Map<String,?> allSharedPrefs;
    String mCityName,mCountryName;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("prayer", " BootCompletedReceiver onReceive: " + intent.getAction() +
                SharedPrefsHelper.getValue(context, Constants.ISHA));
        mCityName = SharedPrefsHelper.getValue(context,context.getString(R.string.cityname));
        mCountryName = SharedPrefsHelper.getValue(context,context.getString(R.string.countryname));
        allSharedPrefs = SharedPrefsHelper.getAllSharedPrefs(context);
        Set<String> sharedPrefsKeys = allSharedPrefs.keySet();
        for (String s:sharedPrefsKeys){
            String[] sharedPrefsValues = SharedPrefsHelper.getValue(context,s).split(":");
            Log.d("prayer", "onReceive: " + sharedPrefsValues[0] + " : " + sharedPrefsValues[1]);
            if (sharedPrefsValues[1] == "true"){
                Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(Utility.getCurrentDate())),
                        sharedPrefsValues[0],
                        Utility.createPendingIntent(context,s,sharedPrefsValues[0],mCityName,mCountryName));
            }
        }
    }
}
