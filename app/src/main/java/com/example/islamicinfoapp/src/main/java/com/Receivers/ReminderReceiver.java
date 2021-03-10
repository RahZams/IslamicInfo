package com.example.islamicinfoapp.src.main.java.com.Receivers;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.RenderScript;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.KeyEventDispatcher;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.view.MainActivity;

import java.util.List;

public class ReminderReceiver extends BroadcastReceiver {
    private NotificationManagerCompat mNotificationManagerCompat;
    String mNamazName,mNamazTime,mTitle,mDesc,mCityName;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("prayer", "onReceive: ");
        mNamazName = intent.getStringExtra(context.getResources().getString(R.string.namazName));
        mNamazTime = intent.getStringExtra(context.getResources().getString(R.string.namazTime));
        mCityName = intent.getStringExtra(context.getResources().getString(R.string.cityname));
        Log.d("prayer", "onReceive: " + mCityName);
        switch(mNamazName){
            case Constants.FAJR:
                mTitle = context.getResources().getString(R.string.fajr_title_notif) + " " + mCityName + " " + mNamazTime;
                mDesc = context.getResources().getString(R.string.fajr_desc);
                break;
            case Constants.SUNRISE:
                mTitle = context.getResources().getString(R.string.sunrise_title_notif) + " " + mCityName + " " + mNamazTime;
                mDesc = context.getResources().getString(R.string.sunrise_desc);
                break;
            case Constants.DHUHR:
                mTitle = context.getResources().getString(R.string.dhuhr_title_notif) + " " + mCityName + " " + mNamazTime;
                mDesc = context.getResources().getString(R.string.dhuhr_desc);
                break;
            case Constants.ASR:
                mTitle = context.getResources().getString(R.string.asr_title_notif) + " " + mCityName + " " + mNamazTime;
                mDesc = context.getResources().getString(R.string.asr_desc);
                break;
            case Constants.MAGHRIB:
                mTitle = context.getResources().getString(R.string.maghrib_title_notif) + " " + mCityName + " " + mNamazTime;
                mDesc = context.getResources().getString(R.string.maghrib_desc);
                break;
            case Constants.ISHA:
                mTitle = context.getResources().getString(R.string.isha_title_notif) + " " + mCityName + " " + mNamazTime;
                mDesc = context.getResources().getString(R.string.isha_desc);
                break;
            default:
                break;
        }

        checkRunningApp(context);

        mNotificationManagerCompat = NotificationManagerCompat.from(context);
        Intent startAppIntent = new Intent(context, MainActivity.class);
        startAppIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,startAppIntent,0);

        Notification notification = new NotificationCompat.Builder(context,Constants.NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_quran)
                .setContentTitle(mTitle)
                .setContentText(mDesc)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mDesc))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();

        mNotificationManagerCompat.notify(Constants.NOTIFICATION_ID,notification);

    }

    private void checkRunningApp(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
        Log.d("prayer", "checkRunningApp: " + tasks.get(0).baseActivity.getPackageName());

        if (tasks.get(0).baseActivity.getPackageName().equals("com.example.islamicinfoapp")){
            Log.d("prayer", "checkRunningApp: " + "your app");
        }
    }
}
