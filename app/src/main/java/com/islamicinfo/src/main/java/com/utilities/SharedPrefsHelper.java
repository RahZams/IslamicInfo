package com.islamicinfo.src.main.java.com.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.islamicinfo.src.main.java.com.model.Constants;

import java.util.Map;

public class SharedPrefsHelper {
    public static SharedPreferences.Editor mEditor;
    private static SharedPreferences mSharedPreferences;
    public static final String SHARED_PREFS = "APP_SHARED_PREFS";
    private static final String TAG = SharedPrefsHelper.class.getSimpleName();

    private static SharedPreferences getPrefs(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static void storeValue(Context context, String key, boolean value) {
        Log.d(Constants.PRAYER_TAG, TAG + " storeValue: " + "key:" + key + " value:" + value);
        if (mSharedPreferences == null)
            mSharedPreferences = getPrefs(context);

        if (mEditor == null)
            mEditor = mSharedPreferences.edit();

            mEditor.putBoolean(key,value).apply();
    }

    public static void storeValue(Context context, String key, String value) {
        Log.d(Constants.PRAYER_TAG, TAG + " storeValue: " + "key:" + key + " value:" + value);
        if (mSharedPreferences == null)
            mSharedPreferences = getPrefs(context);

        if (mEditor == null)
            mEditor = mSharedPreferences.edit();

        mEditor.putString(key,value).apply();
    }

    public static String getValue(Context context,String key){
        if (mSharedPreferences == null){
            mSharedPreferences = getPrefs(context);
        }
        //return mSharedPreferences.getBoolean(key,false);
        return mSharedPreferences.getString(key,"");
    }

    public static Map<String,?> getAllSharedPrefs(Context context){
       if (mSharedPreferences == null){
           mSharedPreferences = getPrefs(context);
       }
       return mSharedPreferences.getAll();
    }

    public static void clearSharedPrefs(Context context,String key){
        if (mSharedPreferences == null){
            mSharedPreferences = getPrefs(context);
        }
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
        }

        mEditor.remove(key).commit();
    }

//    public static void storePendingIntentId(Context mContext, String namazName, int id) {
//        if (mSharedPreferences == null){
//            mSharedPreferences = getPrefs(mContext);
//        }
//        if (mEditor == null){
//            mEditor = mSharedPreferences.edit();
//            mEditor.putInt(namazName,id);
//        }
//    }
//
//    public static int getPendingIntentId(Context mContext, String namazName){
//        if (mSharedPreferences == null){
//            mSharedPreferences = getPrefs(mContext);
//        }
//        return mSharedPreferences.getInt(namazName,0);
//    }
}
