package com.example.islamicinfoapp.src.main.java.com.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.DuasViewModel;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.SurahViewModel;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.internal.Util;

public class SplashActivity extends AppCompatActivity {
    private DuasViewModel mDuasViewModel;
    private SurahViewModel mSurahViewModel;
    private static final String TAG = SplashActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //binding.splashImage.setImageResource(R.drawable.splash_image_modifed);
        mDuasViewModel = ViewModelProviders.of(this).get(DuasViewModel.class);
        mSurahViewModel = ViewModelProviders.of(this).get(SurahViewModel.class);
        startNextActivity();
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
//                mduasDeleteTask =  new DeleteTask();
//                mduasDeleteTask.execute();
                //finish();
                //startActivity(mainIntent);
            }
        },10*1000);
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

    private void checkIfDataAvailableInDatabase() {
        Log.d(Constants.SPLASH, TAG + " onChanged:current date :  " + Utility.getCurrentDate());
        QuranDatabase.getInstance(this).quranDao().getSurahDataCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer surah_count) {
                Log.d(Constants.SPLASH, TAG + " onChanged:surah data count is :  " + surah_count);
                if (surah_count != Integer.valueOf(getString(R.string.surah_total_count))){
                    Log.d(Constants.SPLASH, TAG + " onChanged: integer is not 30");
                    mSurahViewModel.fetchFromRemote();
                }
            }
        });

        QuranDatabase.getInstance(this).quranDao().getQuranDataCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d(Constants.SPLASH, TAG + " onChanged:quranDataCount is :" + integer);
                if (integer == 0) {
                    Log.d(Constants.SPLASH, TAG + " onChanged: integer is 0");
                    mDuasViewModel.fetchFromRemote();
                }
            }
        });



//        QuranDatabase.getInstance(this).quranDao().getAllOldRecords(Utility.getCurrentDate())
//                .observe(this, new Observer<PrayerTiming>() {
//                    @Override
//                    public void onChanged(PrayerTiming prayerTiming) {
//                        if (prayerTiming != null) {
//                            Log.d(Constants.SPLASH, TAG + " onChanged: all records" + prayerTiming.getPrayerTimeEngDate());
//                        }
//                        else{
//                            Log.d(Constants.SPLASH, TAG + " onChanged: no records");
//                        }
//                    }
//                });


//        Completable deletedrecordsCompletable = QuranDatabase.getInstance(this).quranDao().deletePrayerTimesData(Utility.getCurrentDate());
//        deletedrecordsCompletable.subscribeOn(Schedulers.io()).
//                subscribe(new CompletableObserver() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(Constants.SPLASH,TAG +  " prayertiming delete completable's onComplete: ");
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.d(Constants.SPLASH,TAG +  " prayertiming delete completable's onError: " + e.getCause() + e.getLocalizedMessage());
//            }
//        });

        QuranDatabase.getInstance(this).quranDao().deletePrayerTimeData(Utility.getCurrentDate(),Utility.getTomorrowDateForDb())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d(Constants.PRAYER_TAG,TAG + " onComplete: deletePrayerTimeData");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(Constants.PRAYER_TAG,TAG + " onComplete: deletePrayerTimeData" + e.getLocalizedMessage());
                    }
                });

        //int deletedrecords = QuranDatabase.getInstance(this).quranDao().deletePrayerTimeData(Utility.getCurrentDate());
        //Log.d(Constants.SPLASH,TAG  + " checkIfDataAvailableInDatabase: deleted prayertiming records are: " + deletedrecords);

//        QuranDatabase.getInstance(this).quranDao().deletePrayerTimeData(Utility.getCurrentDate())
//        .observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                Log.d(Constants.SPLASH, TAG + " onChanged: deleted records are integer: " + integer);
//            }
//        });

    }

    /** NOT USED */

//    private class DeleteTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            mQuranDao = QuranDatabase.getInstance(getApplication()).quranDao();
//            mQuranDao.deleteAllQuranData();
//            mQuranDao.deleteAllSurahData();
//            mQuranDao.deletePrayerTimeData(utility.getCurrentDate());
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            mDuasViewModel.fetchFromRemote();
//            mSurahViewModel.fetchFromRemote();
//        }
//    }
}
