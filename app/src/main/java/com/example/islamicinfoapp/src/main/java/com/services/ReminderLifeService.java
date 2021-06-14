package com.example.islamicinfoapp.src.main.java.com.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleService;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTiming;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApi;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApiService;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.utilities.PrayerTimeDeserializer;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ReminderLifeService extends LifecycleService {

    private QuranApi mQuranApi;
    private String city, country, namazName;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String BASE_URL = "https://api.aladhan.com/v1/";
    int NOTIFICATION_ID = 0;
    int count = 0;
    private static final String TAG = ReminderLifeService.class.getSimpleName();

    public ReminderLifeService() {
        Log.d(Constants.PRAYER_TAG, TAG);
        mQuranApi = QuranApiService.getRetrofitInstance(BASE_URL,
                PrayerTiming.class, new PrayerTimeDeserializer()).create(QuranApi.class);
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.PRAYER_TAG, TAG + " onCreate: ");
//        String channelId = Utility.createNotificationChannel(this);
//        Notification notification = Utility.createNotification(this,
//                channelId, NOTIFICATION_ID);
//        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.PRAYER_TAG,TAG +  " onStartCommand: ");

//        String channelId = Utility.createNotificationChannel(this);
//        Notification notification = Utility.createNotification(this,
//                channelId, NOTIFICATION_ID);
//        startForeground(NOTIFICATION_ID, notification);

        if (intent != null) {
            city = intent.getStringExtra(getResources().getString(R.string.cityname));
            country = intent.getStringExtra(getResources().getString(R.string.countryname));
            namazName = intent.getStringExtra(getResources().getString(R.string.namazName));
            Log.d(Constants.PRAYER_TAG, TAG + " onStartCommand: " + city + country + namazName);
            checkIfDataAvailableInDb(city, country, Utility.getTomorrowDateForDb(), namazName);
        }
        //return START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void checkIfDataAvailableInDb(String city, String country, String tomorrowDate, String namazName) {
        Log.d(Constants.PRAYER_TAG,TAG  + " checkIfDataAvailableInDb: " + city  + country + tomorrowDate + namazName);
        QuranDatabase.getInstance(this).quranDao().getRecordCount(city, country, tomorrowDate)
                .observe((LifecycleOwner) this, new androidx.lifecycle.Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        Log.d(Constants.PRAYER_TAG, TAG + " checkIfDataAvailableInDb onChanged: " + " integer " + integer);
                        if (integer == 0) {
                            getDataFromApi(city, country, Utility.getTomorrowDateForApi(), namazName);
                        } else {
                            getDataFromDb(city, country, tomorrowDate, namazName);
                        }
                    }
                });
    }

    private void getDataFromDb(String city, String country, String tomorrowDate, String namazName) {
        Log.d(Constants.PRAYER_TAG,TAG + " getDataFromDb: " + city + country + tomorrowDate + namazName);
        QuranDatabase.getInstance(this).quranDao().getPrayerTimingOfCity(city, country, tomorrowDate)
                .observe((LifecycleOwner) this, new androidx.lifecycle.Observer<PrayerTiming>() {
                    @Override
                    public void onChanged(PrayerTiming prayerTiming) {
                        setUpReminders(prayerTiming, namazName);
                    }
                });
    }

    private void getDataFromApi(String city, String country, String tomorrowDate, String namazName) {
        Log.d(Constants.PRAYER_TAG,TAG +" getDataFromApi: " + city + country + tomorrowDate + namazName);
        int method = Integer.parseInt(getApplicationContext().getResources().
                getString(R.string.prayer_time_calculation_method));
        mQuranApi.getPrayerTimings(tomorrowDate, city, country, method)
                .timeout(1000, TimeUnit.SECONDS)
                .retryWhen(throwableObservable -> throwableObservable.flatMap(error -> {
                    if (error instanceof TimeoutException) {
                        Log.d(Constants.PRAYER_TAG, TAG + "getDataFromApi: error");
                        return Observable.just(new Object());
                    } else {
                        return Observable.error(error);
                    }
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Response<PrayerTiming>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<PrayerTiming> prayerTimingResponse) {
                        PrayerTiming prayerTiming = (PrayerTiming) prayerTimingResponse.body();
                        if(prayerTiming != null) {
                            count = count + 1;
                            prayerTiming.setCity(city);
                            prayerTiming.setCountry(country);
                            Log.d(Constants.PRAYER_TAG, TAG + " onNext: count" + count);
                            Log.d(Constants.PRAYER_TAG, TAG + " onNext: " + prayerTiming.toString());
                            Log.d(Constants.PRAYER_TAG, TAG + " onSuccess: " + prayerTiming.getPrayerTimeEngDate() +
                                    Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())));
                            insertDataToDb(prayerTiming, namazName);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void setUpReminders(PrayerTiming prayerTiming, String namazName) {
        Log.d(Constants.PRAYER_TAG,TAG +  " setUpReminders: " + namazName);
        PendingIntent pendingIntent;
        String sharedPrefsValue = "";
        switch (namazName) {
            case Constants.FAJR:
                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + namazName);
                pendingIntent = Utility.createPendingIntent(this,
                        Constants.FAJR, Utility.changeTimeFormat(prayerTiming.getFajr()), city, country);
                sharedPrefsValue = Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())) + "," +
                        Utility.changeTimeFormat(prayerTiming.getFajr()) + ",true";
                SharedPrefsHelper.
                        storeValue(this, this.namazName, sharedPrefsValue);
                Utility.setupReminder(this,
                        Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())),
                        Utility.changeTimeFormat(prayerTiming.getFajr()), pendingIntent);
                break;
            case Constants.SUNRISE:
                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
                pendingIntent = Utility.createPendingIntent(this,
                        Constants.SUNRISE, Utility.changeTimeFormat(prayerTiming.getSunsrise()), city, country);
                sharedPrefsValue = Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())) + "," +
                        Utility.changeTimeFormat(prayerTiming.getSunsrise()) + ",true";
                SharedPrefsHelper.storeValue(this, this.namazName, sharedPrefsValue);
                Utility.setupReminder(this, Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())),
                        Utility.changeTimeFormat(prayerTiming.getSunsrise()), pendingIntent);
                break;
            case Constants.DHUHR:
                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
                pendingIntent = Utility.createPendingIntent(this, Constants.DHUHR,
                        Utility.changeTimeFormat(prayerTiming.getDhuhr()), city, country);
                sharedPrefsValue = Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())) + "," +
                        Utility.changeTimeFormat(prayerTiming.getDhuhr()) + ",true";
                SharedPrefsHelper.storeValue(this, this.namazName, sharedPrefsValue);
                Utility.setupReminder(this, Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())),
                        Utility.changeTimeFormat(prayerTiming.getDhuhr()), pendingIntent);
                break;
            case Constants.ASR:
                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
                pendingIntent = Utility.createPendingIntent(this, Constants.ASR,
                        Utility.changeTimeFormat(prayerTiming.getAsr()), city, country);
                sharedPrefsValue = Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())) + "," +
                        Utility.changeTimeFormat(prayerTiming.getAsr()) + ",true";
                SharedPrefsHelper.storeValue(this, this.namazName, sharedPrefsValue);
                Utility.setupReminder(this, Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())),
                        Utility.changeTimeFormat(prayerTiming.getAsr()), pendingIntent);
                break;
            case Constants.MAGHRIB:
                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
                pendingIntent = Utility.createPendingIntent(this, Constants.MAGHRIB,
                        Utility.changeTimeFormat(prayerTiming.getMaghrib()), city, country);
                sharedPrefsValue = Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())) + "," +
                        Utility.changeTimeFormat(prayerTiming.getMaghrib()) + ",true";
                SharedPrefsHelper.storeValue(this, this.namazName, sharedPrefsValue);
                Utility.setupReminder(this, Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())),
                        Utility.changeTimeFormat(prayerTiming.getMaghrib()), pendingIntent);
                break;
            case Constants.ISHA:
                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
                pendingIntent = Utility.createPendingIntent(this, Constants.ISHA,
                        Utility.changeTimeFormat(prayerTiming.getIsha()), city, country);
                sharedPrefsValue = Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())) + "," +
                        Utility.changeTimeFormat(prayerTiming.getIsha()) + ",true";
                SharedPrefsHelper.storeValue(this, this.namazName, sharedPrefsValue);
                Utility.setupReminder(this, Utility.getDateForApi(Utility.convertStringToDate(prayerTiming.getPrayerTimeEngDate())),
                        Utility.changeTimeFormat(prayerTiming.getIsha()), pendingIntent);
                break;
            default:
                break;
        }
        //stopSelf();
    }
    private void insertDataToDb(PrayerTiming prayerTiming, String namazName) {
        Log.d(Constants.PRAYER_TAG,TAG + " insertDataToDb: " + namazName);
        Completable completable = QuranDatabase.getInstance(this).quranDao().insert(prayerTiming);
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                Log.d(Constants.PRAYER_TAG, TAG + "onComplete: observer ");
                setUpReminders(prayerTiming, namazName);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(Constants.PRAYER_TAG, "onError: ");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.PRAYER_TAG,TAG  + " onDestroy: ");
        mCompositeDisposable.clear();
    }
}
