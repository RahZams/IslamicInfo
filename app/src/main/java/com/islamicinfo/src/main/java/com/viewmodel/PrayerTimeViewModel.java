package com.islamicinfo.src.main.java.com.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.PrayerTiming;
import com.islamicinfo.src.main.java.com.model.QuranApi;
import com.islamicinfo.src.main.java.com.model.QuranApiService;
import com.islamicinfo.src.main.java.com.model.QuranDatabase;
import com.islamicinfo.src.main.java.com.utilities.PrayerTimeDeserializer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class PrayerTimeViewModel extends AndroidViewModel {
    private static final String TAG = PrayerTimeViewModel.class.getSimpleName();
    public MutableLiveData<PrayerTiming> mTimingMutableLiveData = new MutableLiveData<>();
    private QuranApi mQuranApi;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    //private DBTask mdDbTask;
//    private RetrieveTask mRetrieveTask;
//    private DbCountTask mDbCountTask;

    public PrayerTimeViewModel(@NonNull Application application) {
        super(application);
        mQuranApi = QuranApiService.getRetrofitInstance(getApplication().getResources().
                getString(R.string.prayer_times_base_url),PrayerTiming.class,
                new PrayerTimeDeserializer()).create(QuranApi.class);
    }

//

    public void fetchFromRemote(String city,String country,String date){
        Log.d("prayer", "fetchFromRemote: " + "date: " + date);
        int method = Integer.parseInt(getApplication().getResources().getString(R.string.prayer_time_calculation_method));
        //Observable<PrayerTiming> prayerTimingObservable = mQuranApi.getPrayerTimings(date,city,country,method);
//        Observable<Long> timer = Observable.timer(1000, TimeUnit.MILLISECONDS);
//        Observable.zip(prayerTimingObservable,timer,)

        //mQuranApi.getPrayerTiming(city,country,method,date)
        mQuranApi.getPrayerTimings(date,city,country,method)
                .timeout(1000,TimeUnit.SECONDS)
                .retryWhen(throwableObservable -> throwableObservable.flatMap(error ->{
                    if (error instanceof TimeoutException){
                        Log.d("prayer", "fetchFromRemote: error");
                        return Observable.just(new Object());
                    }else{
                        return Observable.error(error);
                    }
        }))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Response<PrayerTiming>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Response<PrayerTiming> prayerTimingResponse) {
                        Log.d("prayer", "onNext: response code: " + prayerTimingResponse.code() + " message: " + prayerTimingResponse.message() +
                                " body: " + prayerTimingResponse.body() +
                               " successful:" + prayerTimingResponse.isSuccessful());
                        PrayerTiming prayerTiming = (PrayerTiming) prayerTimingResponse.body();
                        prayerTiming.setCity(city);
                        prayerTiming.setCountry(country);
                        insertDataToDb(prayerTiming);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.d("prayer", "onError: " + e.getLocalizedMessage() + e.getCause());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void insertDataToDb(PrayerTiming prayerTiming) {
        /*Completable.fromCallable(() -> QuranDatabase.getInstance(getApplication()).quranDao().insert(new PrayerTiming(
                "fajr","sunrise","dhuhr","asr","sunset","maghrib","isha","imsak",
                "engdate","hijridate","day",1,"monthname",
                "year")))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getLocalizedMessage());
                    }
                });*/
        Completable completable = QuranDatabase.getInstance(getApplication()).quranDao().insert(prayerTiming);
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                Log.d("prayer", "onComplete observer");
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.d("prayer", "onError: " + "message:" + e.getMessage() + " " + "cause:" + e.getCause());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }

    /*Log.d("MY_APP","inside InsertToDB:"+prayerTiming.toString());
        Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                Log.d("MY_APP","Inside run... Before insert");
                QuranDatabase.getInstance(getApplication()).quranDao().insert(prayerTiming);
                Log.d("MY_APP","Inside run after insert");
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread())
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("MY_APP","OnComplete...!!!");
            }

            @Override
            public void onError(Throwable e) {

            }
        });*/
    //}

//    public void fetchFromRemote(String city,String country){
//        Log.d("prayer", "fetchFromRemote: ");
//        int method = Integer.parseInt(getApplication().getResources().getString(R.string.prayer_time_calculation_method));
//        mQuranApi.getPrayerTiming(city,country,method)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new Observer<PrayerTiming>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(PrayerTiming prayerTiming) {
//                        //mdDbTask = new DBTask();
//                        Log.d(TAG, "onNext: " + prayerTiming.getPrayerTimeEngDate());
//                        prayerTiming.setCity(city);
//                        prayerTiming.setCountry(country);
//                        //insertDataToDb(prayerTiming);
//                        mTimingMutableLiveData.setValue(prayerTiming);
//                        //mdDbTask.execute(prayerTiming);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

//    public void fetchFromRemote(String city,String country){
//        int method = Integer.parseInt(getApplication().getResources().getString(R.string.prayer_time_calculation_method));
//        mQuranApi.getPrayerTiming(city,country,method).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<PrayerTiming>() {
//                    @Override
//                    public void onSuccess(PrayerTiming prayerTiming) {
//                        mdDbTask = new DBTask();
//                        Log.d("prayer", "onSuccess: " + prayerTiming.getPrayerTimeEngDate());
//                        prayerTiming.setCity(city);
//                        prayerTiming.setCountry(country);
//                        mdDbTask.execute(prayerTiming);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("prayer", "onError: " + e.getMessage());
//                    }
//                });
//    }


//    public void fetchFromDatabase(String city,String country,String date){
//        mRetrieveTask = new RetrieveTask();
//        mRetrieveTask.execute(city + "-" + country + "-" + date);
//    }

    //public void fetchRecordCountFromDatabase(String cityName, String countryName, String formattedDate) {
    //    mDbCountTask = new DbCountTask();
     //   mDbCountTask.execute(cityName + "-" + countryName + "-" + formattedDate);
//        final AtomicInteger atomicInteger = new AtomicInteger();
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int count = QuranDatabase.getInstance(getApplication()).quranDao().getRecordCount(cityName, countryName, formattedDate);
//                Log.d("prayer", "run: " + count);
//                atomicInteger.set(count);
//            }
//        });
//        t.start();
//        return atomicInteger.get();
    //}

//    public boolean checkIfExists(String city, String country, String date){
//        return QuranDatabase.getInstance(getApplication()).quranDao().isExists(city,country,date);
//    }
//
//    private class DBTask extends AsyncTask<PrayerTiming,Void,Void>{
//
//        @Override
//        protected Void doInBackground(PrayerTiming... prayerTimings) {
//            PrayerTiming prayerTiming = prayerTimings[0];
//            Log.d("prayer", "doInBackground: " + prayerTiming.getCity());
//            long result = QuranDatabase.getInstance(getApplication()).quranDao().insert(prayerTiming);
//            Log.d("prayer", "doInBackground: " + "result " + result);
//            return null;
//        }
//    }

//    private class RetrieveTask extends AsyncTask<String,Void,PrayerTiming>{
//
//        @Override
//        protected PrayerTiming doInBackground(String... strings) {
//            String city = strings[0].split("-")[0];
//            String country = strings[0].split("-")[1];
//            String date = strings[0].split("-")[2];
//            return QuranDatabase.getInstance(getApplication()).quranDao().getPrayerTimingOfCity(city,country,date);
//        }
//
//        @Override
//        protected void onPostExecute(PrayerTiming prayerTiming) {
//            mTimingMutableLiveData.setValue(prayerTiming);
//            if (prayerTiming != null){
//                Log.d("prayer", "onPostExecute: " + prayerTiming.getPrayerTimeEngDate());
//            }
//            else{
//                Log.d("prayer", "onPostExecute: " + "prayer timing null");
//            }
//        }
//    }

//    private class DbCountTask extends AsyncTask<String,Void,Integer> {
//        @Override
//        protected Integer doInBackground(String... strings) {
//            String city = strings[0].split("-")[0];
//            String country = strings[0].split("-")[1];
//            String date = strings[0].split("-")[2];
//            return QuranDatabase.getInstance(getApplication()).quranDao().getRecordCount(city,country,date);
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//            Toast.makeText(getApplication(), "count" + integer, Toast.LENGTH_SHORT).show();
//        }
//    }
}
