package com.example.islamicinfoapp.src.main.java.com.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.renderscript.RenderScript;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;

public class ReminderReceiver extends BroadcastReceiver {
    private NotificationManagerCompat mNotificationManagerCompat;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("prayer", "onReceive: ");
        mNotificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context,Constants.NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_quran)
                .setContentTitle("title")
                .setContentText("content text")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();
        mNotificationManagerCompat.notify(Constants.NOTIFICATION_ID,notification);

    }
}
