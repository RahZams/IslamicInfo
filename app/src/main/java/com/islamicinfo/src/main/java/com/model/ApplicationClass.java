package com.islamicinfo.src.main.java.com.model;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.islamicinfo.R;

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initNotifChannels();
    }

    private void initNotifChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL,
                    getResources().getString(R.string.notif_channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(getResources().getString(R.string.notif_channel_desc));
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
