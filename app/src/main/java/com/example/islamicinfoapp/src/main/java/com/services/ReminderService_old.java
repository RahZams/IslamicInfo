package com.example.islamicinfoapp.src.main.java.com.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;

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

public class ReminderService_old extends Service {

    private QuranApi mQuranApi;
    private String city, country, namazName;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String BASE_URL = "https://api.aladhan.com/v1/";
    int NOTIFICATION_ID = 0;
    private static final String TAG = ReminderService.class.getSimpleName();

    public ReminderService_old() {
        Log.d(Constants.PRAYER_TAG, TAG );
        mQuranApi = QuranApiService.getRetrofitInstance(BASE_URL,
                PrayerTiming.class, new PrayerTimeDeserializer()).create(QuranApi.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        String channelId = Utility.createNotificationChannel(ReminderService_old.this);
        Notification notification = Utility.createNotification(ReminderService_old.this,
                channelId, NOTIFICATION_ID);
        startForeground(NOTIFICATION_ID, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            city = intent.getStringExtra(getResources().getString(R.string.cityname));
            country = intent.getStringExtra(getResources().getString(R.string.countryname));
            namazName = intent.getStringExtra(getResources().getString(R.string.namazName));
            Log.d(Constants.PRAYER_TAG, TAG  + " onStartCommand: " + city + country + namazName);
            //checkIfDataAvailableInDb(city,country,Utility.getTomorrowDate(),namazName);
            //getDataFromApi(city, country, Utility.getTomorrowDate(), namazName);
//            mQuranApi.getPrayerTimings(Utility.getTomorrowDate(), city, country, method)
//                    .timeout(1000, TimeUnit.SECONDS)
//                    .retryWhen(throwableObservable -> throwableObservable.flatMap(error -> {
//                        if (error instanceof TimeoutException) {
//                            Log.d("prayer", "fetchFromRemote: error");
//                            return Observable.just(new Object());
//                        } else {
//                            return Observable.error(error);
//                        }
//                    }))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(Schedulers.newThread())
//                    .subscribe(new Observer<Response<PrayerTiming>>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            mCompositeDisposable.add(d);
//                        }
//
//                        @Override
//                        public void onNext(@NonNull Response<PrayerTiming> prayerTimingResponse) {
//                            PendingIntent pendingIntent;
//                            String sharedPrefsValue = "";
//                            PrayerTiming prayerTiming = (PrayerTiming) prayerTimingResponse.body();
//                            prayerTiming.setCity(city);
//                            prayerTiming.setCountry(country);
//                            insertDataToDb(prayerTiming);
//                            //mTimingMutableLiveData.setValue(prayerTiming);
//                            Log.d("prayer", "onNext: " + prayerTiming.toString());
//                            Log.d("prayer", "onSuccess: " + prayerTiming.getPrayerTimeEngDate() +
//                                    Utility.getTomorrowDate());
//                            switch (namazName) {
//                                case Constants.FAJR:
//                                    Log.d("prayer", "onNext:switch case " + namazName);
//                                    pendingIntent = Utility.createPendingIntent(ReminderService.this,
//                                            Constants.FAJR, Utility.changeTimeFormat(prayerTiming.getFajr()), city, country);
//                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getFajr(),pendingIntent);
////                                    Utility.setupReminder(ReminderService.this, Utility.getDateForApi
////                                            (Utility.convertStringToDate(Utility.getTomorrowDate())), "1:15 PM", pendingIntent);
//                                    sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                            Utility.changeTimeFormat(prayerTiming.getFajr()) + ",true";
//                                    SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                    Utility.setupReminder(ReminderService.this,
//                                            Utility.getTomorrowDate(), Utility.changeTimeFormat(prayerTiming.getFajr()), pendingIntent);
//                                    break;
//                                case Constants.SUNRISE:
//                                    Log.d("prayer", "onNext:switch case " + namazName);
//                                    pendingIntent = Utility.createPendingIntent(ReminderService.this,
//                                            Constants.SUNRISE, Utility.changeTimeFormat(prayerTiming.getSunsrise()), city, country);
//                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getSunsrise(),pendingIntent);
//                                    sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                            Utility.changeTimeFormat(prayerTiming.getSunsrise()) + ",true";
//                                    SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                    Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                            Utility.changeTimeFormat(prayerTiming.getSunsrise()), pendingIntent);
//                                    break;
//                                case Constants.DHUHR:
//                                    Log.d("prayer", "onNext:switch case " + namazName);
//                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.DHUHR,
//                                            Utility.changeTimeFormat(prayerTiming.getDhuhr()), city, country);
//                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getDhuhr(),pendingIntent);
//                                    sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                            Utility.changeTimeFormat(prayerTiming.getDhuhr()) + ",true";
//                                    SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                    Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                            Utility.changeTimeFormat(prayerTiming.getDhuhr()), pendingIntent);
//                                    break;
//                                case Constants.ASR:
//                                    Log.d("prayer", "onNext:switch case " + namazName);
//                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.ASR,
//                                            Utility.changeTimeFormat(prayerTiming.getAsr()), city, country);
//                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getAsr(),pendingIntent);
//                                    sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                            Utility.changeTimeFormat(prayerTiming.getAsr()) + ",true";
//                                    SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                    Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                            Utility.changeTimeFormat(prayerTiming.getAsr()), pendingIntent);
//                                    break;
//                                case Constants.MAGHRIB:
//                                    Log.d("prayer", "onNext:switch case " + namazName);
//                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.MAGHRIB,
//                                            Utility.changeTimeFormat(prayerTiming.getMaghrib()), city, country);
//                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getMaghrib(),pendingIntent);
//                                    sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                            Utility.changeTimeFormat(prayerTiming.getMaghrib()) + ",true";
//                                    SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                    Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                            Utility.changeTimeFormat(prayerTiming.getMaghrib()), pendingIntent);
//                                    break;
//                                case Constants.ISHA:
//                                    Log.d("prayer", "onNext:switch case " + namazName);
//                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.ISHA,
//                                            Utility.changeTimeFormat(prayerTiming.getIsha()), city, country);
//                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getIsha(),pendingIntent);
//                                    sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                            Utility.changeTimeFormat(prayerTiming.getIsha()) + ",true";
//                                    SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                    Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                            Utility.changeTimeFormat(prayerTiming.getIsha()), pendingIntent);
//                                    break;
//                                default:
//                                    break;
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                        }
//
//                        @Override
//                        public void onComplete() {
//                        }
//                    });

            //        mQuranApi.getPrayerTimings(Utility.getTomorrowDate(),city,country,method)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Observer<PrayerTiming>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        mCompositeDisposable.add(d);
//                    }
//
//                    @Override
//                    public void onNext(PrayerTiming prayerTiming) {
//                        PendingIntent pendingIntent;
//                        prayerTiming.setCity(city);
//                        prayerTiming.setCountry(country);
//                        //mTimingMutableLiveData.setValue(prayerTiming);
//                        Log.d("prayer", "onNext: "+prayerTiming.toString());
//                        Log.d("prayer", "onSuccess: " + prayerTiming.getPrayerTimeEngDate());
//                        switch (namazName){
//                            case Constants.FAJR:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.FAJR,prayerTiming.getFajr(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getFajr(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.SUNRISE:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.SUNRISE,prayerTiming.getSunsrise(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getSunsrise(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.DHUHR:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.DHUHR,prayerTiming.getDhuhr(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getDhuhr(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.ASR:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.ASR,prayerTiming.getAsr(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getAsr(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.MAGHRIB:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.MAGHRIB,prayerTiming.getMaghrib(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getMaghrib(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.ISHA:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.ISHA,prayerTiming.getIsha(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getIsha(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        }
        return START_STICKY;
    }

    private void checkIfDataAvailableInDb(String city, String country, String tomorrowDate,String namazName) {
        QuranDatabase.getInstance(ReminderService_old.this).quranDao().getRecordCount(city,country,tomorrowDate)
                .observe((LifecycleOwner) ReminderService_old.this, new androidx.lifecycle.Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        Log.d("prayer", "onChanged: " + " integer "+ integer);
                        if (integer == 0){
                            getDataFromApi(city,country,tomorrowDate,namazName);
                        }
                        else{
                            getDataFromDb(city,country,tomorrowDate,namazName);
                        }
                    }
                });
    }

    private void getDataFromDb(String city, String country, String tomorrowDate,String namazName) {
        QuranDatabase.getInstance(ReminderService_old.this).quranDao().getPrayerTimingOfCity(city,country,tomorrowDate)
                .observe((LifecycleOwner) ReminderService_old.this, new androidx.lifecycle.Observer<PrayerTiming>() {
                    @Override
                    public void onChanged(PrayerTiming prayerTiming) {
                        //setUpReminders(prayerTiming,namazName);
                    }
                });
    }

    private void getDataFromApi(String city, String country, String tomorrowDate, String namazName) {
        int method = Integer.parseInt(getApplicationContext().getResources().
                getString(R.string.prayer_time_calculation_method));
        mQuranApi.getPrayerTimings(tomorrowDate,city,country,method)
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
//                        PendingIntent pendingIntent;
//                        String sharedPrefsValue = "";
                        PrayerTiming prayerTiming = (PrayerTiming) prayerTimingResponse.body();
                        prayerTiming.setCity(city);
                        prayerTiming.setCountry(country);
                        insertDataToDb(prayerTiming,namazName);
                        //mTimingMutableLiveData.setValue(prayerTiming);
                        Log.d(Constants.PRAYER_TAG, "onNext: " + prayerTiming.toString());
                        Log.d(Constants.PRAYER_TAG, "onSuccess: " + prayerTiming.getPrayerTimeEngDate());
                        //setUpReminders(prayerTiming,namazName);
//                        switch (namazName) {
//                            case Constants.FAJR:
//                                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + namazName);
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,
//                                        Constants.FAJR, Utility.changeTimeFormat(prayerTiming.getFajr()), city, country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getFajr(),pendingIntent);
////                                    Utility.setupReminder(ReminderService.this, Utility.getDateForApi
////                                            (Utility.convertStringToDate(Utility.getTomorrowDate())), "1:15 PM", pendingIntent);
//                                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                        Utility.changeTimeFormat(prayerTiming.getFajr()) + ",true";
//                                SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                Utility.setupReminder(ReminderService.this,
//                                        Utility.getTomorrowDate(), Utility.changeTimeFormat(prayerTiming.getFajr()), pendingIntent);
//                                break;
//                            case Constants.SUNRISE:
//                                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + namazName);
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,
//                                        Constants.SUNRISE, Utility.changeTimeFormat(prayerTiming.getSunsrise()), city, country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getSunsrise(),pendingIntent);
//                                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                        Utility.changeTimeFormat(prayerTiming.getSunsrise()) + ",true";
//                                SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                        Utility.changeTimeFormat(prayerTiming.getSunsrise()), pendingIntent);
//                                break;
//                            case Constants.DHUHR:
//                                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + namazName);
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.DHUHR,
//                                        Utility.changeTimeFormat(prayerTiming.getDhuhr()), city, country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getDhuhr(),pendingIntent);
//                                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                        Utility.changeTimeFormat(prayerTiming.getDhuhr()) + ",true";
//                                SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                        Utility.changeTimeFormat(prayerTiming.getDhuhr()), pendingIntent);
//                                break;
//                            case Constants.ASR:
//                                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + namazName);
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.ASR,
//                                        Utility.changeTimeFormat(prayerTiming.getAsr()), city, country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getAsr(),pendingIntent);
//                                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                        Utility.changeTimeFormat(prayerTiming.getAsr()) + ",true";
//                                SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                        Utility.changeTimeFormat(prayerTiming.getAsr()), pendingIntent);
//                                break;
//                            case Constants.MAGHRIB:
//                                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + namazName);
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.MAGHRIB,
//                                        Utility.changeTimeFormat(prayerTiming.getMaghrib()), city, country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getMaghrib(),pendingIntent);
//                                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                        Utility.changeTimeFormat(prayerTiming.getMaghrib()) + ",true";
//                                SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                        Utility.changeTimeFormat(prayerTiming.getMaghrib()), pendingIntent);
//                                break;
//                            case Constants.ISHA:
//                                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + namazName);
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.ISHA,
//                                        Utility.changeTimeFormat(prayerTiming.getIsha()), city, country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getIsha(),pendingIntent);
//                                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                                        Utility.changeTimeFormat(prayerTiming.getIsha()) + ",true";
//                                SharedPrefsHelper.storeValue(ReminderService.this, namazName, sharedPrefsValue);
//                                Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(),
//                                        Utility.changeTimeFormat(prayerTiming.getIsha()), pendingIntent);
//                                break;
//                            default:
//                                break;
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        //        mQuranApi.getPrayerTimings(Utility.getTomorrowDate(),city,country,method)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Observer<PrayerTiming>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        mCompositeDisposable.add(d);
//                    }
//
//                    @Override
//                    public void onNext(PrayerTiming prayerTiming) {
//                        PendingIntent pendingIntent;
//                        prayerTiming.setCity(city);
//                        prayerTiming.setCountry(country);
//                        //mTimingMutableLiveData.setValue(prayerTiming);
//                        Log.d("prayer", "onNext: "+prayerTiming.toString());
//                        Log.d("prayer", "onSuccess: " + prayerTiming.getPrayerTimeEngDate());
//                        switch (namazName){
//                            case Constants.FAJR:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.FAJR,prayerTiming.getFajr(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getFajr(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.SUNRISE:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.SUNRISE,prayerTiming.getSunsrise(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getSunsrise(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.DHUHR:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.DHUHR,prayerTiming.getDhuhr(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getDhuhr(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.ASR:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.ASR,prayerTiming.getAsr(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getAsr(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.MAGHRIB:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.MAGHRIB,prayerTiming.getMaghrib(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getMaghrib(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            case Constants.ISHA:
//                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.ISHA,prayerTiming.getIsha(),city,country);
//                                //Utility.setupReminder(ReminderService.this,prayerTiming.getIsha(),pendingIntent);
//                                Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                        (Utility.convertStringToDate(Utility.getTomorrowDate())), "4:45 PM",pendingIntent);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }

//    private void setUpReminders(PrayerTiming prayerTiming, String namazName) {
//        PendingIntent pendingIntent;
//        String sharedPrefsValue = "";
//        switch (namazName) {
//            case Constants.FAJR:
//                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + namazName);
//                pendingIntent = Utility.createPendingIntent(ReminderService_old.this,
//                        Constants.FAJR, Utility.changeTimeFormat(prayerTiming.getFajr()), city, country);
//                //Utility.setupReminder(ReminderService.this,prayerTiming.getFajr(),pendingIntent);
////                                    Utility.setupReminder(ReminderService.this, Utility.getDateForApi
////                                            (Utility.convertStringToDate(Utility.getTomorrowDate())), "1:15 PM", pendingIntent);
//                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                        Utility.changeTimeFormat(prayerTiming.getFajr()) + ",true";
//                SharedPrefsHelper.storeValue(ReminderService_old.this, this.namazName, sharedPrefsValue);
//                Utility.setupReminder(ReminderService_old.this,
//                        Utility.getTomorrowDate(), Utility.changeTimeFormat(prayerTiming.getFajr()), pendingIntent);
//                break;
//            case Constants.SUNRISE:
//                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
//                pendingIntent = Utility.createPendingIntent(ReminderService_old.this,
//                        Constants.SUNRISE, Utility.changeTimeFormat(prayerTiming.getSunsrise()), city, country);
//                //Utility.setupReminder(ReminderService.this,prayerTiming.getSunsrise(),pendingIntent);
//                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                        Utility.changeTimeFormat(prayerTiming.getSunsrise()) + ",true";
//                SharedPrefsHelper.storeValue(ReminderService_old.this, this.namazName, sharedPrefsValue);
//                Utility.setupReminder(ReminderService_old.this, Utility.getTomorrowDate(),
//                        Utility.changeTimeFormat(prayerTiming.getSunsrise()), pendingIntent);
//                break;
//            case Constants.DHUHR:
//                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
//                pendingIntent = Utility.createPendingIntent(ReminderService_old.this, Constants.DHUHR,
//                        Utility.changeTimeFormat(prayerTiming.getDhuhr()), city, country);
//                //Utility.setupReminder(ReminderService.this,prayerTiming.getDhuhr(),pendingIntent);
//                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                        Utility.changeTimeFormat(prayerTiming.getDhuhr()) + ",true";
//                SharedPrefsHelper.storeValue(ReminderService_old.this, this.namazName, sharedPrefsValue);
//                Utility.setupReminder(ReminderService_old.this, Utility.getTomorrowDate(),
//                        Utility.changeTimeFormat(prayerTiming.getDhuhr()), pendingIntent);
//                break;
//            case Constants.ASR:
//                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
//                pendingIntent = Utility.createPendingIntent(ReminderService_old.this, Constants.ASR,
//                        Utility.changeTimeFormat(prayerTiming.getAsr()), city, country);
//                //Utility.setupReminder(ReminderService.this,prayerTiming.getAsr(),pendingIntent);
//                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                        Utility.changeTimeFormat(prayerTiming.getAsr()) + ",true";
//                SharedPrefsHelper.storeValue(ReminderService_old.this, this.namazName, sharedPrefsValue);
//                Utility.setupReminder(ReminderService_old.this, Utility.getTomorrowDate(),
//                        Utility.changeTimeFormat(prayerTiming.getAsr()), pendingIntent);
//                break;
//            case Constants.MAGHRIB:
//                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
//                pendingIntent = Utility.createPendingIntent(ReminderService_old.this, Constants.MAGHRIB,
//                        Utility.changeTimeFormat(prayerTiming.getMaghrib()), city, country);
//                //Utility.setupReminder(ReminderService.this,prayerTiming.getMaghrib(),pendingIntent);
//                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                        Utility.changeTimeFormat(prayerTiming.getMaghrib()) + ",true";
//                SharedPrefsHelper.storeValue(ReminderService_old.this, this.namazName, sharedPrefsValue);
//                Utility.setupReminder(ReminderService_old.this, Utility.getTomorrowDate(),
//                        Utility.changeTimeFormat(prayerTiming.getMaghrib()), pendingIntent);
//                break;
//            case Constants.ISHA:
//                Log.d(Constants.PRAYER_TAG, "onNext:switch case " + this.namazName);
//                pendingIntent = Utility.createPendingIntent(ReminderService_old.this, Constants.ISHA,
//                        Utility.changeTimeFormat(prayerTiming.getIsha()), city, country);
//                //Utility.setupReminder(ReminderService.this,prayerTiming.getIsha(),pendingIntent);
//                sharedPrefsValue = Utility.getTomorrowDate() + "," +
//                        Utility.changeTimeFormat(prayerTiming.getIsha()) + ",true";
//                SharedPrefsHelper.storeValue(ReminderService_old.this, this.namazName, sharedPrefsValue);
//                Utility.setupReminder(ReminderService_old.this, Utility.getTomorrowDate(),
//                        Utility.changeTimeFormat(prayerTiming.getIsha()), pendingIntent);
//                break;
//            default:
//                break;
//        }
//    }

    private void insertDataToDb(PrayerTiming prayerTiming, String namazName) {
        Completable completable = QuranDatabase.getInstance(ReminderService_old.this).quranDao().insert(prayerTiming);
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                Log.d(Constants.PRAYER_TAG, TAG + "onComplete: observer ");
                //setUpReminders(prayerTiming,namazName);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(Constants.PRAYER_TAG, "onError: ");
            }
        });
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            startForeground(NOTIFICATION_ID,new Notification.Builder(this).build());
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
