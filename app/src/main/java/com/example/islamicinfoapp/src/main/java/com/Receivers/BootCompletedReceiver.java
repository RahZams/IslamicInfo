package com.example.islamicinfoapp.src.main.java.com.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;

import java.util.Map;
import java.util.Set;

public class BootCompletedReceiver extends BroadcastReceiver {
    Map<String,?> allSharedPrefs;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("prayer", " BootCompletedReceiver onReceive: " + intent.getAction() +
                SharedPrefsHelper.getValue(context, Constants.ISHA));
        allSharedPrefs = SharedPrefsHelper.getAllSharedPrefs(context);
        Set<String> sharedPrefsKeys = allSharedPrefs.keySet();
        for (String s:sharedPrefsKeys){
            String[] sharedPrefsValues = SharedPrefsHelper.getValue(context,s).split(":");
            if (sharedPrefsValues[1] == "true"){
                Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(Utility.getCurrentDate())),
                        sharedPrefsValues[0],
                        Utility.createPendingIntent(context,s,sharedPrefsValues[0],"",""));
            }
        }
        

    }
}
