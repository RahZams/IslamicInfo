package com.example.islamicinfoapp.src.main.java.Services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTiming;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApi;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApiService;
import com.example.islamicinfoapp.src.main.java.com.utilities.PrayerTimeDeserializer;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ReminderService extends Service {

    private QuranApi mQuranApi;
    private String city,country,namazName;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String BASE_URL = "https://api.aladhan.com/v1/";
    public ReminderService() {
        Log.d("prayer", "ReminderService: ");
        mQuranApi = QuranApiService.getRetrofitInstance(BASE_URL,
                PrayerTiming.class,new PrayerTimeDeserializer()).create(QuranApi.class);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int method = Integer.parseInt(getApplicationContext().getResources().getString(R.string.prayer_time_calculation_method));
        if(intent != null) {
            city = intent.getStringExtra(getResources().getString(R.string.cityname));
            country = intent.getStringExtra(getResources().getString(R.string.countryname));
            namazName = intent.getStringExtra(getResources().getString(R.string.namazName));
            Log.d("prayer", "onStartCommand: " + city + country + namazName);
            mQuranApi.getPrayerTimings(Utility.getTomorrowDate(), city, country, method)
                    .timeout(1000, TimeUnit.SECONDS)
                    .retryWhen(throwableObservable -> throwableObservable.flatMap(error -> {
                        if (error instanceof TimeoutException) {
                            Log.d("prayer", "fetchFromRemote: error");
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
                            PendingIntent pendingIntent;
                            PrayerTiming prayerTiming = (PrayerTiming) prayerTimingResponse.body();
                            prayerTiming.setCity(city);
                            prayerTiming.setCountry(country);
                            //mTimingMutableLiveData.setValue(prayerTiming);
                            Log.d("prayer", "onNext: " + prayerTiming.toString());
                            Log.d("prayer", "onSuccess: " + prayerTiming.getPrayerTimeEngDate());
                            switch (namazName) {
                                case Constants.FAJR:
                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.FAJR, prayerTiming.getFajr(), city, country);
                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getFajr(),pendingIntent);
//                                    Utility.setupReminder(ReminderService.this, Utility.getDateForApi
//                                            (Utility.convertStringToDate(Utility.getTomorrowDate())), "1:15 PM", pendingIntent);
                                    Utility.setupReminder(ReminderService.this, Utility.getTomorrowDate(), "12:48 AM", pendingIntent);
                                    break;
                                case Constants.SUNRISE:
                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.SUNRISE, prayerTiming.getSunsrise(), city, country);
                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getSunsrise(),pendingIntent);
                                    Utility.setupReminder(ReminderService.this,Utility.getTomorrowDate(), "12:48 AM", pendingIntent);
                                    break;
                                case Constants.DHUHR:
                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.DHUHR, prayerTiming.getDhuhr(), city, country);
                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getDhuhr(),pendingIntent);
                                    Utility.setupReminder(ReminderService.this,Utility.getTomorrowDate(), "12:48 AM", pendingIntent);
                                    break;
                                case Constants.ASR:
                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.ASR, prayerTiming.getAsr(), city, country);
                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getAsr(),pendingIntent);
                                    Utility.setupReminder(ReminderService.this,Utility.getTomorrowDate(), "12:48 AM", pendingIntent);
                                    break;
                                case Constants.MAGHRIB:
                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.MAGHRIB, prayerTiming.getMaghrib(), city, country);
                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getMaghrib(),pendingIntent);
                                    Utility.setupReminder(ReminderService.this,Utility.getTomorrowDate(), "12:48 AM", pendingIntent);
                                    break;
                                case Constants.ISHA:
                                    pendingIntent = Utility.createPendingIntent(ReminderService.this, Constants.ISHA, prayerTiming.getIsha(), city, country);
                                    //Utility.setupReminder(ReminderService.this,prayerTiming.getIsha(),pendingIntent);
                                    Utility.setupReminder(ReminderService.this,Utility.getTomorrowDate(), "12:48 AM", pendingIntent);
                                    break;
                                default:
                                    break;
                            }
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
        return START_STICKY;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
