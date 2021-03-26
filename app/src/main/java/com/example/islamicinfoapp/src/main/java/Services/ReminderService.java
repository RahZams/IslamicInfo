package com.example.islamicinfoapp.src.main.java.Services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTiming;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApi;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApiService;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.utilities.PrayerTimeDeserializer;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.PrayerTimeViewModel;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReminderService extends Service {

    private QuranApi mQuranApi;
    private String city,country,namazName;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public ReminderService() {
        Log.d("prayer", "ReminderService: ");
        mQuranApi = QuranApiService.getRetrofitInstance(this.getResources().getString(R.string.prayer_times_base_url),
                PrayerTiming.class,new PrayerTimeDeserializer()).create(QuranApi.class);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int method = Integer.parseInt(getApplication().getResources().getString(R.string.prayer_time_calculation_method));
        city = intent.getStringExtra(getResources().getString(R.string.cityname));
        country = intent.getStringExtra(getResources().getString(R.string.countryname));
        namazName = intent.getStringExtra(getResources().getString(R.string.namazName));
        Log.d("prayer", "onStartCommand: " + city + country + namazName);
        mQuranApi.getPrayerTimings(Utility.getTomorrowDate(),city,country,method)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<PrayerTiming>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(PrayerTiming prayerTiming) {
                        PendingIntent pendingIntent;
                        prayerTiming.setCity(city);
                        prayerTiming.setCountry(country);
                        //mTimingMutableLiveData.setValue(prayerTiming);
                        Log.d("prayer", "onNext: "+prayerTiming.toString());
                        Log.d("prayer", "onSuccess: " + prayerTiming.getPrayerTimeEngDate());
                        switch (namazName){
                            case Constants.FAJR:
                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.FAJR,prayerTiming.getFajr(),city,country);
                                //Utility.setupReminder(ReminderService.this,prayerTiming.getFajr(),pendingIntent);
                                Utility.setupReminder(ReminderService.this,"7:10 PM",pendingIntent);
                                break;
                            case Constants.SUNRISE:
                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.SUNRISE,prayerTiming.getSunsrise(),city,country);
                                //Utility.setupReminder(ReminderService.this,prayerTiming.getSunsrise(),pendingIntent);
                                Utility.setupReminder(ReminderService.this,"7:15 PM",pendingIntent);
                                break;
                            case Constants.DHUHR:
                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.DHUHR,prayerTiming.getDhuhr(),city,country);
                                //Utility.setupReminder(ReminderService.this,prayerTiming.getDhuhr(),pendingIntent);
                                Utility.setupReminder(ReminderService.this,"7:17 PM",pendingIntent);
                                break;
                            case Constants.ASR:
                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.ASR,prayerTiming.getAsr(),city,country);
                                //Utility.setupReminder(ReminderService.this,prayerTiming.getAsr(),pendingIntent);
                                Utility.setupReminder(ReminderService.this,"4:25 PM",pendingIntent);
                                break;
                            case Constants.MAGHRIB:
                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.MAGHRIB,prayerTiming.getMaghrib(),city,country);
                                //Utility.setupReminder(ReminderService.this,prayerTiming.getMaghrib(),pendingIntent);
                                Utility.setupReminder(ReminderService.this,"4:25 PM",pendingIntent);
                                break;
                            case Constants.ISHA:
                                pendingIntent = Utility.createPendingIntent(ReminderService.this,Constants.ISHA,prayerTiming.getIsha(),city,country);
                                //Utility.setupReminder(ReminderService.this,prayerTiming.getIsha(),pendingIntent);
                                Utility.setupReminder(ReminderService.this,"6:25 PM",pendingIntent);
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
