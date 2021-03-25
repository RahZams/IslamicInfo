package com.example.islamicinfoapp.src.main.java.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.islamicinfoapp.R;
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
        mQuranApi = QuranApiService.getRetrofitInstance(getApplication().getResources().
                getString(R.string.prayer_times_base_url), PrayerTiming.class,new PrayerTimeDeserializer()).create(QuranApi.class);

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
                        prayerTiming.setCity(city);
                        prayerTiming.setCountry(country);
                        //mTimingMutableLiveData.setValue(prayerTiming);
                        Log.d("prayer", "onNext: "+prayerTiming.toString());
                        Log.d("prayer", "onSuccess: " + prayerTiming.getPrayerTimeEngDate());;

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
