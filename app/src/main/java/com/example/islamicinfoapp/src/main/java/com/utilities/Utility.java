package com.example.islamicinfoapp.src.main.java.com.utilities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.Receivers.ReminderReceiver;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {
    private ConnectivityManager mConnectivityManager;

    public static String getCurrentDate() {
        //Log.d("date", "getCurrentDate: " + Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat sf = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        Log.d("prayer", "getCurrentDate: " + Locale.getDefault() + sf.format(new Date()));
        return sf.format(new Date());
    }

    public static String getDateForDb(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        return sf.format(date);
    }

    public static String getDateForApi(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        Log.d("prayer", "getDateForApi: " + "input date"  + date  + sf.format(date));
        return sf.format(date);
    }

    public static String getTomorrowDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,1);
        return getDateForApi(c.getTime());
    }

    public static Date convertStringToDate(String dateValue){
        Log.d("prayer", "convertStringToDate: " + "dateValue" + dateValue);
        Date convertedDate = null;
        SimpleDateFormat sf = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        try {
            convertedDate = sf.parse(dateValue);
            Log.d("prayer", "convertStringToDate: " + "within try" + convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("prayer", "convertStringToDate: " + convertedDate);
        return convertedDate;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a",Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static void playSound(Context context) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mediaPlayer = MediaPlayer.create(context,uri);
        mediaPlayer.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String createNotificationChannel(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId  = "SERVICE_CHANNEL_ID";
        String channelName = "Service Notification Channel";
        NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,
                NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationManager.createNotificationChannel(notificationChannel);
        return channelId;

    }

    public static Notification createNotification(Context context, String channelId, int notification_id) {
        NotificationCompat.Builder builder = new NotificationCompat.
                Builder(context,channelId);
        Notification notification = builder.setOngoing(true)
                .setContentTitle("service example")
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        return notification;
    }

    public boolean checkForNetworkAvailibility(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mConnectivityManager.getActiveNetworkInfo()!= null && mConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void redirectingToProvideConnection(Context context, int ic_drawable) {
        Intent intent = null;
        if (ic_drawable == R.drawable.ic_no_network){
            intent = new Intent(Settings.ACTION_SETTINGS);
        }
        else if (ic_drawable == R.drawable.ic_location_off){
            intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        }

        context.startActivity(intent);
    }

    public boolean checkForLocationConnection(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if(!(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) && !(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))){
            return false;
        }
        else {
            return true;
        }

    }

    public static String changeTimeFormat(String timeData) {
        Date dateTime;
        String finalTime = "";
        try {
            if (timeData.endsWith("AM") || timeData.endsWith("PM") || timeData.endsWith("am") || timeData.endsWith("pm")){
                Log.d("prayer", "changeDateFormat: " + "if am/pm");
                dateTime = new SimpleDateFormat("hh:mm a").parse(timeData);
                finalTime = new SimpleDateFormat("hh:mm").format(dateTime);
            }
            else if (!timeData.startsWith("12")) {
                Log.d("prayer", "changeDateFormat: if");
                dateTime = new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(timeData);
                finalTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateTime);
            }
            else if (timeData.startsWith("12")){
                Log.d("prayer", "changeDateFormat: else");
                finalTime = timeData + " " + "PM";
            }
            Log.d("prayer", "changeDateFormat: " + finalTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            DateTimeFormatter parser = DateTimeFormatter.ofPattern("hh:mm",Locale.ENGLISH);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm aa",Locale.ENGLISH);
//            LocalTime time = LocalTime.parse(timeData,parser);
//            finalTime = time.format(formatter);
//        }
        return finalTime;
    }

    public static PendingIntent createPendingIntent(Context mContext, String namazName, String namazTiming, String mCityName,String mCountryName){
        Intent intent = new Intent(mContext, ReminderReceiver.class);
        intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
        intent.putExtra(mContext.getResources().getString(R.string.countryname),mCountryName);
        intent.putExtra(mContext.getResources().getString(R.string.namazName),
                namazName);
        intent.putExtra(mContext.getResources().getString(R.string.namazTime),
                namazTiming);
        PendingIntent pendingIntent = null;
        switch(namazName){
            case Constants.FAJR:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.FAJR_ID,intent,0);
                break;
            case Constants.SUNRISE:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.SUNRISE_ID,intent,0);
                break;
            case Constants.DHUHR:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.DHUHR_ID,intent,0);
                break;
            case Constants.ASR:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ASR_ID,intent,0);
                break;
            case Constants.MAGHRIB:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.MAGHRIB_ID,intent,0);
                break;
            case Constants.ISHA:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ISHA_ID,intent,0);
                break;
            default:
                break;
        }
        Log.d("prayer", "createPendingIntent: " + "namazName" + namazName + "pendingIntent" + pendingIntent);
        return pendingIntent;
    }

    public static void setupReminder(Context mContext, String currentDate, String namazTiming, PendingIntent pendingIntent) {
        Log.d("prayer", "setupReminder: " + "time" + namazTiming);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        //namazTiming = Utility.changeDateFormat(namazTiming);
        Log.d("prayer", "setupReminder: " + namazTiming);
        String[] initTiming = namazTiming.split(" ");
        String[] timing = initTiming[0].split(":");
        String[] dateArray = currentDate.split("-");
        Log.d("prayer", "setupReminder: length" + timing.length + timing[0] + timing[1]);

        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(System.currentTimeMillis());
        //cal.set(Calendar.DATE,Integer.valueOf(currentDate));
        Log.d("prayer", "setupReminder: dateArray" + dateArray[0] + dateArray[1] + dateArray[2]
        + "Integer.parseInt(timing[0])" + Integer.parseInt(timing[0]));
        cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateArray[0]));
        cal.set(Calendar.MONTH,Integer.parseInt(dateArray[1]) - 1);
        cal.set(Calendar.YEAR,Integer.parseInt(dateArray[2]));
        cal.set(Calendar.HOUR, Integer.parseInt(timing[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(timing[1]));
        cal.set(Calendar.SECOND,0);
        Log.d("prayer", "setupReminder: " + initTiming[1]);
        if (initTiming[1].equals("AM")){
            Log.d("prayer", "setupReminder: " + " if AM");
            cal.set(Calendar.AM_PM,Calendar.AM);
        }
        else if (initTiming[1].equals("PM")){
            Log.d("prayer", "setupReminder: " + "if PM");
            cal.set(Calendar.AM_PM,Calendar.PM);
        }

        Log.d("prayer", "setupReminder: " + "hour:" + cal.get(Calendar.HOUR_OF_DAY) +
                "minute:"+ cal.get(Calendar.MINUTE) + "am/pm:" + cal.get(Calendar.AM_PM));
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),24*60*60*1000,pendingIntent);
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            Log.d("prayer", "setupReminder: " + "M" + cal.getTimeInMillis());
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Log.d("prayer", "setupReminder: " + "kitkat");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        }
        else{
            Log.d("prayer", "setupReminder: " + "else");
            alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        }
    }

//    public static void cancelReminder(Context mContext,PendingIntent pendingIntent) {
//        Log.d("prayer", "cancelReminder: ");
//        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(pendingIntent);
//    }

    public static void cancelReminder(Context mContext, String namazName) {
        Log.d("prayer", "cancelReminder: ");
        Intent intent = new Intent(mContext,ReminderReceiver.class);
        PendingIntent pendingIntent = null;
        switch (namazName){
            case Constants.FAJR:
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.FAJR_ID,intent,0);
                break;
            case Constants.SUNRISE:
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.SUNRISE_ID,intent,0);
                break;
            case Constants.DHUHR:
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.DHUHR_ID,intent,0);
                break;
            case Constants.ASR:
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ASR_ID,intent,0);
                break;
            case Constants.MAGHRIB:
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.MAGHRIB_ID,intent,0);
                break;
            case Constants.ISHA:
                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ISHA_ID,intent,0);
                break;
            default:
                break;
        }
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (pendingIntent != null) {
            Log.d("prayer", "cancelReminder: " + "namazName" + namazName + "pendingIntent" + pendingIntent);
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
