package com.example.islamicinfoapp.src.main.java.com.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.view.LocationActivity;
import com.example.islamicinfoapp.src.main.java.com.view.MainActivity;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.PrayerTimeViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class LocListener implements LocationListener {
    private Context mContext;
    private LocationManager mLocationManager;
    private String mProvider;
    public static final int LOCATION_REQUEST_CODE = 1;
    private PrayerTimeViewModel mPrayerTimeViewModel;
    private Utility mUtility;
    private CallMainActivityCallback callMainActivityCallback;
    private static final String TAG = LocListener.class.getSimpleName();

    public LocListener(LocationActivity context) {
        this.mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mPrayerTimeViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(PrayerTimeViewModel.class);
        mUtility = new Utility();
        callMainActivityCallback = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        String cityName = "",countryName = "";
        if (location == null){
            cityName = mContext.getResources().getString(R.string.default_city);
            countryName = mContext.getResources().getString(R.string.default_country);
            checkIfDataAvailableInDatabase(cityName,countryName);
            //getPrayerTimesDataFromApi(cityName, countryName);
        }
        //Log.d("prayer", "onLocationChanged: " + location.toString());
        else {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            cityName = addresses.get(0).getAddressLine(0);
//            stateName = addresses.get(0).getAddressLine(1);
//            countryName = addresses.get(0).getAddressLine(2);
//            Log.d("prayer", "onLocationChanged: " + "admin area" + addresses.get(0).getAdminArea() +
//                    "getCountryName" + addresses.get(0).getCountryName() + "getLocality" + addresses.get(0).getLocality()
//            + "getPremises" + addresses.get(0).getPremises() + "getSubAdminArea" + addresses.get(0).getSubAdminArea()
//            + "getSubLocality" + addresses.get(0).getSubLocality());
                cityName = addresses.get(0).getLocality();
                countryName = addresses.get(0).getCountryName();
                //getPrayerTimesDataFromApi(cityName,countryName,Utility.getDateForApi(Utility.convertStringToDate("03/03/2021")));
                //getPrayerTimesDataFromApi(cityName,countryName,Utility.getDateForApi(Utility.convertStringToDate("30/03/2021")));
                checkIfDataAvailableInDatabase(cityName,countryName);
                callMainActivityCallback.callMainAcitivity(cityName,countryName);
             //   getPrayerTimesDataFromApi(cityName, countryName);
                Log.d(Constants.PRAYER_TAG, TAG + " onLocationChanged: " + cityName + " " + countryName);
                //SharedPrefsHelper.storeValue(mContext,mContext.getString(R.string.cityname),cityName);
                //SharedPrefsHelper.storeValue(mContext,mContext.getString(R.string.countryname),countryName);
                Toast.makeText(mContext, "city:" + cityName + " country:" + countryName, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        saveCityCountryToSharedPrefs(cityName,countryName);
        checkAndCreateNewAlarms();
    }

    private void saveCityCountryToSharedPrefs(String cityName, String countryName) {
        SharedPrefsHelper.storeValue(mContext,mContext.getString(R.string.cityname),cityName);
        SharedPrefsHelper.storeValue(mContext,mContext.getString(R.string.countryname),countryName);
    }

    private void checkAndCreateNewAlarms() {
    }

    private void checkIfDataAvailableInDatabase(String cityName, String countryName) {
        QuranDatabase.getInstance(mContext).quranDao().getRecordCount(cityName,countryName,Utility.getDateForDb(new Date()))
                .observe((LifecycleOwner) mContext, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        Log.d(Constants.PRAYER_TAG, TAG + " onChanged: " + " integer "+ integer);
                        if (integer == 0){
                            // cancel alarms here
                            cancelAllExistingAlarms();
                            SharedPrefsHelper.storeValue(mContext,mContext.getResources().getString(R.string.new_location),
                                    cityName + "," + countryName);
                            getPrayerTimesDataFromApi(cityName,countryName,Utility.getDateForApi(new Date()));
                        }
                    }
                });
    }

    private void cancelAllExistingAlarms() {
        Log.d(Constants.PRAYER_TAG,TAG + " cancelAllExistingAlarms: ");
        Map<String,?> allSharedPrefs;
        Set<String> sharedPrefsKeys;

        allSharedPrefs = SharedPrefsHelper.getAllSharedPrefs(mContext);
        sharedPrefsKeys = allSharedPrefs.keySet();
        for (String s:sharedPrefsKeys){
            Log.d(Constants.PRAYER_TAG,TAG +  " cancelAllExistingAlarms: s " + s);
            if (SharedPrefsHelper.getValue(mContext,s).contains(",")){
                String[] sharedPrefsValues = SharedPrefsHelper.getValue(mContext,s).split(",");
                Log.d(Constants.PRAYER_TAG,TAG +  " cancelAllExistingAlarms:sharedPrefsValues " + sharedPrefsValues[0] + " "
                + sharedPrefsValues[1] + " " + sharedPrefsValues[2]);
                if (!(sharedPrefsValues[2].isEmpty()) && sharedPrefsValues[2].equals("true")){
                    Utility.cancelReminder(mContext,s);
                }
            }
        }

    }

    private void getPrayerTimesDataFromApi(String cityName, String countryName,String formattedDate) {
        //String formattedDate = mUtility.getCurrentDate();
        Log.d(Constants.PRAYER_TAG, TAG + " getPrayerTimesDataFromApi: " + cityName + countryName + formattedDate);
        //boolean exists = mPrayerTimeVewModel.checkIfExists(cityName,countryName,formattedDate);
        //Log.d("date", "getPrayerTimesDataFromApi: " + exists);
        mPrayerTimeViewModel.fetchFromRemote(cityName,countryName,formattedDate);
        //mPrayerTimeViewModel.fetchFromDatabase(cityName,countryName,getCurrentDate());

        //mPrayerTimeViewModel.fetchRecordCountFromDatabase(cityName, countryName, formattedDate);
        //Log.d("prayer", "getPrayerTimesDataFromApi: " + count);
//        if (count == 0) {
//            Log.d("prayer", "getPrayerTimesDataFromApi: ");
//            mPrayerTimeViewModel.fetchFromRemote(cityName, countryName);
//        } else {
//            Log.d("prayer", "getPrayerTimesDataFromApi: " + "not null");
//            Intent intent = new Intent(mContext, MainActivity.class);
//            mContext.startActivity(intent);
//        }
    }

//    private void makeMainActivityCall(String cityName, String countryName) {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Intent intent = new Intent(mContext, MainActivity.class);
//        intent.putExtra(mContext.getString(R.string.cityname),cityName);
//        intent.putExtra(mContext.getString(R.string.countryname),countryName);
//        mContext.startActivity(intent);
//    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

//    public void findLocation() {
//        Criteria criteria = new Criteria();
//        mProvider = mLocationManager.getBestProvider(criteria,true);
//        Toast.makeText(mContext, "mprovider" + (mProvider == null?"null":"not null") , Toast.LENGTH_SHORT).show();
//        if (ContextCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            // TODO: Consider calling
//            //    Activity#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return;
//        }
//        Location location = mLocationManager.getLastKnownLocation(mProvider);
//        Toast.makeText(mContext, "location" + (location == null?"null":"not null"), Toast.LENGTH_SHORT).show();
//        //onLocationChanged(location);
//
//    }

    public void findLocation() {
        List<String> mProviders = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : mProviders) {
            if (ContextCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            Toast.makeText(mContext, "provider" + provider, Toast.LENGTH_SHORT).show();
            //Log.d("prayer", "findLocation: " + "provider" + provider);
            Location location = mLocationManager.getLastKnownLocation(provider);
            if (location == null){
                continue;
            }
            if (bestLocation == null){
                Log.d(Constants.PRAYER_TAG, TAG + " findLocation: " + "provider" + provider);
                    bestLocation = location;
            }
        }
//        if (bestLocation == null){
//
//        }
        Log.d(Constants.PRAYER_TAG, TAG + " findLocation: " + bestLocation);
        onLocationChanged(bestLocation);
    }


    public void requestPermission() {
//        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            if ((ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,Manifest.permission.ACCESS_FINE_LOCATION))){
//                ActivityCompat.requestPermissions((Activity) mContext,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
//
//            }
//            else{
//                ActivityCompat.requestPermissions((Activity) mContext,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
//            }
//        }

        if (ContextCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_FINE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }
        else{
            findLocation();
        }
    }

    public void showAlertDialog(Context context, int ic_drawable, String title, String message, String buttonText){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(ic_drawable);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonText, (dialog, which) -> {
            if (ic_drawable == 0){
                requestPermission();
            }
            else {
                mUtility.redirectingToProvideConnection(context,ic_drawable);
            }
            dialog.dismiss();
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(mContext.getResources().getColor(R.color.colorPrimary)));
        alertDialog.show();
    }

//    public String getCurrentDate() {+
//        //Log.d("date", "getCurrentDate: " + Calendar.getInstance().getTimeInMillis());
//        SimpleDateFormat sf = new SimpleDateFormat("dd MMM yyyy");
//        return sf.format(new Date());
//    }

    public interface CallMainActivityCallback{
        void callMainAcitivity(String city,String country);
    }
}
