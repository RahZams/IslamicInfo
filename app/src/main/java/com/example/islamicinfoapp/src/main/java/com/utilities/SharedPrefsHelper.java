package com.example.islamicinfoapp.src.main.java.com.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPrefsHelper {
    public static SharedPreferences.Editor mEditor;
    private static SharedPreferences mSharedPreferences;
    public static final String SHARED_PREFS = "APP_SHARED_PREFS";

    private static SharedPreferences getPrefs(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static void storeValue(Context context, String key, boolean value) {
        Log.d("prayer", "storeValue: " + "key:" + key + " value:" + value);
        if (mSharedPreferences == null)
            mSharedPreferences = getPrefs(context);

        if (mEditor == null)
            mEditor = mSharedPreferences.edit();

            mEditor.putBoolean(key,value).apply();

    }
    public static boolean getValue(Context context,String key){
        if (mSharedPreferences == null){
            mSharedPreferences = getPrefs(context);
        }
        return mSharedPreferences.getBoolean(key,false);
    }
}
