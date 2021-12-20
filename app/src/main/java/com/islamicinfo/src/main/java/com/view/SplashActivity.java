package com.islamicinfo.src.main.java.com.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.model.QuranDatabase;
import com.islamicinfo.src.main.java.com.utilities.SharedPrefsHelper;
import com.islamicinfo.src.main.java.com.utilities.Utility;
import com.islamicinfo.src.main.java.com.viewmodel.DuasViewModel;
import com.islamicinfo.src.main.java.com.viewmodel.SurahViewModel;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    private DuasViewModel mDuasViewModel;
    private SurahViewModel mSurahViewModel;
    private static final String TAG = "MY_APP";
    private Intent locIntent;
    private boolean isInternetAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mDuasViewModel = ViewModelProviders.of(this).get(DuasViewModel.class);
        mSurahViewModel = ViewModelProviders.of(this).get(SurahViewModel.class);
        hideStatusBar();

        mSurahViewModel.isRemoteFetched.observe(this,
                isFetched -> {
                    Log.d(Constants.SURAH_TAG,TAG + " is remote FETCHED:"+isFetched);
                    gotoNextActivity();
        });

        fetchData();

    }

    private void hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
           getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        }
        else{
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
    private void gotoNextActivity(){
        Log.d(TAG, "Inside gotoNextActivity: ");
        isInternetAvailable = Utility.checkForNetworkAvailibility(SplashActivity.this);
        Log.d(Constants.SURAH_TAG,TAG + " isInternetAvailable:"+isInternetAvailable);
        if(isInternetAvailable) {
            SplashActivity.this.finish();
            startActivity(locIntent);
        }
    }
    private void fetchData(){
        Log.d(TAG, "fetchData: ");
        Log.d(Constants.SURAH_TAG,TAG + " Inside fetchData");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(Constants.SURAH_TAG,TAG + " Inside run method");
                if(Utility.checkForNetworkAvailibility(SplashActivity.this)){
                    isInternetAvailable=true;
                    checkIfDataAvailableInDatabase();
                    if (Utility.checkForLocationConnection(SplashActivity.this) &&
                            (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                                    PackageManager.PERMISSION_GRANTED) &&
                            SharedPrefsHelper.getValue(SplashActivity.this,getResources().getString(R.string.loc_permission)).equals(
                                    getResources().getString(R.string.dont_ask_again))) {
                        locIntent = new Intent(SplashActivity.this,MainActivity.class);
                        locIntent.putExtra(getResources().getString(R.string.cityname),getResources().getString(R.string.default_city));
                        locIntent.putExtra(getResources().getString(R.string.countryname),getResources().getString(R.string.default_country));
                    }
                    else{
                        locIntent = new Intent(SplashActivity.this,LocationActivity.class);
                    }
                }
                else{
                    isInternetAvailable = false;
                    showAlertDialog(SplashActivity.this,R.drawable.ic_no_network
                            ,getResources().getString(R.string.no_internet),getResources().getString(R.string.no_internet_message),
                            getResources().getString(R.string.ok_button_text));
                }
            }
        },500);
    }

    private void startNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent locIntent;
                if(Utility.checkForNetworkAvailibility(SplashActivity.this)){
                    checkIfDataAvailableInDatabase();
                    if (Utility.checkForLocationConnection(SplashActivity.this) &&
                            (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                                    PackageManager.PERMISSION_GRANTED) &&
                            SharedPrefsHelper.getValue(SplashActivity.this,getResources().getString(R.string.loc_permission)).equals(
                                    getResources().getString(R.string.dont_ask_again))) {
                        locIntent = new Intent(SplashActivity.this,MainActivity.class);
                        locIntent.putExtra(getResources().getString(R.string.cityname),getResources().getString(R.string.default_city));
                        locIntent.putExtra(getResources().getString(R.string.countryname),getResources().getString(R.string.default_country));
                    }
                    else{
                        locIntent = new Intent(SplashActivity.this,LocationActivity.class);
                    }
                    SplashActivity.this.finish();
                    startActivity(locIntent);
                }
                else{
                    showAlertDialog(SplashActivity.this,R.drawable.ic_no_network
                            ,getResources().getString(R.string.no_internet),getResources().getString(R.string.no_internet_message),
                            getResources().getString(R.string.ok_button_text));
                }
            }
        },3*1000);
    }

    private void showAlertDialog(Context context, int drawable, String title, String message, String buttonText){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(drawable);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonText, (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            dialog.dismiss();
            SplashActivity.this.finish();
            startActivity(intent);
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> {
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        });
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void checkIfDataAvailableInDatabase() {
        Log.d(Constants.SURAH_TAG, TAG + " Inside checkIfDataAvailableInDatabase");
        Log.d(Constants.SURAH_TAG, TAG + " onChanged:current date :  " + Utility.getCurrentDate());
        Thread surahThread = new Thread() {
            @Override
            public void run() {
                int surahCount = QuranDatabase.getInstance(SplashActivity.this).quranDao().getSurahCount();
                if (surahCount != 28) {
                    Log.d(Constants.SURAH_TAG, TAG + " 28 Surah's not available in DB");
                    mSurahViewModel.fetchFromRemote();
                } else {
                    Log.d(Constants.SURAH_TAG, TAG + " 28 Surah's available in DB");
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (Utility.checkForLocationConnection(SplashActivity.this) &&
                                    (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                                            PackageManager.PERMISSION_GRANTED) &&
                                    SharedPrefsHelper.getValue(SplashActivity.this, getResources().getString(R.string.loc_permission)).equals(
                                            getResources().getString(R.string.dont_ask_again))) {
                                locIntent = new Intent(SplashActivity.this, MainActivity.class);
                                locIntent.putExtra(getResources().getString(R.string.cityname), getResources().getString(R.string.default_city));
                                locIntent.putExtra(getResources().getString(R.string.countryname), getResources().getString(R.string.default_country));
                            } else {
                                locIntent = new Intent(SplashActivity.this, LocationActivity.class);
                            }
                            gotoNextActivity();
                        }
                    }, 3000);
                }
            }
        };
        surahThread.start();


        Thread thread = new Thread() {
            @Override
            public void run() {
                int duaCount = QuranDatabase.getInstance(SplashActivity.this).quranDao().getDuwaCount();
                if (duaCount == 0) {
                    Log.d(Constants.ZIKR_TAG, TAG + " No dua's available in DB. Fetching from server");
                    mDuasViewModel.fetchFromRemote();
                }
            }
        };
        thread.start();

        QuranDatabase.getInstance(this).quranDao().deletePrayerTimeData(Utility.getCurrentDate(), Utility.getTomorrowDateForDb())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d(Constants.PRAYER_TAG, TAG + " onComplete: deletePrayerTimeData");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(Constants.PRAYER_TAG, TAG + " onComplete: deletePrayerTimeData" + e.getLocalizedMessage());
                    }
                });
    }
}
