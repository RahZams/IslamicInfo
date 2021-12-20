package com.islamicinfo.src.main.java.com.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.utilities.SharedPrefsHelper;
import com.islamicinfo.src.main.java.com.utilities.Utility;

import java.util.Map;
import java.util.Set;

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
        String sharedPrefs = "";
        Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + mCityName + " " + mCountryName);
        allSharedPrefs = SharedPrefsHelper.getAllSharedPrefs(context);
        Set<String> sharedPrefsKeys = allSharedPrefs.keySet();
        Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: " + sharedPrefsKeys.size());
        for (String s:sharedPrefsKeys){
            Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: s" + s);
            if (SharedPrefsHelper.getValue(context,s).contains(",")){
                String[] sharedPrefsValues = SharedPrefsHelper.getValue(context,s).split(",");
                Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: sharedPrefsValues" + sharedPrefsValues[0]
                        + " " + sharedPrefsValues[1] + " " + sharedPrefsValues[2]);
//                if (Utility.compareTwoTimings(sharedPrefsValues[1],Utility.getSystemTime()) &&
//                        sharedPrefsValues[2].equals("true")){
//                    Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: " + "true");
//                    Log.d(Constants.PRAYER_TAG, TAG + " scheduleAlarm: " + sharedPrefsValues[0] + " " + sharedPrefsValues[1]);
//                    Utility.setupReminder(context,sharedPrefsValues[0],sharedPrefsValues[1],
//                            Utility.createPendingIntent(context,s,sharedPrefsValues[1],mCityName,mCountryName));
//                }
                if (sharedPrefsValues[2].equals("true")){
                    Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + "true " + sharedPrefsValues[0] + Utility.getCurrentDate() +
                            Utility.compareDates(context,sharedPrefsValues[0],Utility.getCurrentDate()));
                    if(Utility.compareDates(context,sharedPrefsValues[0],Utility.getCurrentDate())
                            .equals(context.getResources().getString(R.string.equals))){
                        Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + "compare dates equal");
                        if (Utility.compareTwoTimings(sharedPrefsValues[1],Utility.getSystemTime())){
                            Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + "compare timings after");
                                Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(sharedPrefsValues[0]))
                                        ,sharedPrefsValues[1], Utility.createPendingIntent
                                                (context,s,sharedPrefsValues[1],mCityName,mCountryName));
                        }
                        else{
                            Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + "compare timings before");
                                sharedPrefs = Utility.getTomorrowDateForDb() + "," + sharedPrefsValues[1]
                                        + "," + sharedPrefsValues[2];
                                SharedPrefsHelper.storeValue(context,s,sharedPrefs);
                                Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(
                                        Utility.getTomorrowDateForDb())),sharedPrefsValues[1],
                                        Utility.createPendingIntent(context,s,sharedPrefsValues[1],mCityName,mCountryName));
                        }
                    }
                    else if(Utility.compareDates(context,sharedPrefsValues[0],
                            Utility.getCurrentDate()).equals(context.getResources().getString(R.string.after))){
                        Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + "compare dates after");
                        Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + Utility.getDateForApi(Utility.convertStringToDate(
                                sharedPrefsValues[0])));
                        Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(
                                sharedPrefsValues[0])),sharedPrefsValues[1],
                                Utility.createPendingIntent(context,s,sharedPrefsValues[1],mCityName,mCountryName));
                    }
                    else if(Utility.compareDates(context,sharedPrefsValues[0],
                            Utility.getCurrentDate()).equals(context.getResources().getString(R.string.before))){
                        Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + "compare dates before");
                        if (Utility.compareTwoTimings(sharedPrefsValues[1],Utility.getSystemTime())){
                            Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + "compare timings after");
                            sharedPrefs = Utility.getCurrentDate() + "," + sharedPrefsValues[1]
                                    + "," + sharedPrefsValues[2];
                            SharedPrefsHelper.storeValue(context,s,sharedPrefs);
                            Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(Utility.getCurrentDate()))
                                    ,sharedPrefsValues[1], Utility.createPendingIntent(context,s,
                                            sharedPrefsValues[1],mCityName,mCountryName));
                        }
                        else{
                            Log.d(Constants.PRAYER_TAG,TAG +  " scheduleAlarm: " + "compare timings before");
                            sharedPrefs = Utility.getTomorrowDateForDb() + "," + sharedPrefsValues[1]
                                    + "," + sharedPrefsValues[2];
                            SharedPrefsHelper.storeValue(context,s,sharedPrefs);
                            Utility.setupReminder(context,Utility.getDateForApi(Utility.convertStringToDate(
                                    Utility.getTomorrowDateForDb())),sharedPrefsValues[1],
                                    Utility.createPendingIntent(context,s,sharedPrefsValues[1],mCityName,mCountryName));
                        }
                    }
                }
            }
        }
    }
}
