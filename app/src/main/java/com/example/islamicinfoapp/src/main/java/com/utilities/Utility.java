package com.example.islamicinfoapp.src.main.java.com.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.Receivers.ReminderReceiver;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.view.LocationActivity;

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
        Log.d("prayer", "getCurrentDate: " + Locale.getDefault());
        return sf.format(new Date());
    }

    public static String getDateForDb(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        return sf.format(date);
    }

    public static String getDateForApi(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        Log.d("prayer", "getDateForApi: " + sf.format(date));
        return sf.format(date);
    }

    public static String getTomorrowDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,1);
        return getDateForApi(c.getTime());
    }

    public static Date convertStringToDate(String dateValue){
        Date convertedDate = null;
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        try {
            convertedDate = sf.parse(dateValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public boolean checkForNetworkAvailibility(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mConnectivityManager.getActiveNetworkInfo()!= null && mConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void redirectingToProvideConnection(Context context, int ic_drawable) {
        Intent intent = null;
        if (ic_drawable == R.drawable.ic_no_network){
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
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
        return pendingIntent;
    }

    public static void setupReminder(Context mContext,String namazTiming, PendingIntent pendingIntent) {
        Log.d("prayer", "setupReminder: " + "time" + namazTiming);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        //namazTiming = Utility.changeDateFormat(namazTiming);
        Log.d("prayer", "setupReminder: " + namazTiming);
        String[] initTiming = namazTiming.split(" ");
        String[] timing = initTiming[0].split(":");
        Log.d("prayer", "setupReminder: length" + timing.length + timing[0] + timing[1]);

        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timing[0]));
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
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        }
        else{
            alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        }
    }

    public static void cancelReminder(Context mContext,PendingIntent pendingIntent) {
        Log.d("prayer", "cancelReminder: ");
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
