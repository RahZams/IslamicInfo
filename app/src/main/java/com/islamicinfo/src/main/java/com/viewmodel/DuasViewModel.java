package com.islamicinfo.src.main.java.com.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.model.DuaApiData;
import com.islamicinfo.src.main.java.com.model.QuranApi;
import com.islamicinfo.src.main.java.com.model.QuranApiData;
import com.islamicinfo.src.main.java.com.model.QuranApiService;
import com.islamicinfo.src.main.java.com.model.QuranDatabase;
import com.islamicinfo.src.main.java.com.model.QuranDbData;
import com.islamicinfo.src.main.java.com.utilities.DuaDataDeserializer;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DuasViewModel extends AndroidViewModel {

    private QuranApi mQuranApi;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private static final String TAG = DuasViewModel.class.getSimpleName();

    public DuasViewModel(@NonNull Application application) {
        super(application);
        mQuranApi = QuranApiService.getRetrofitInstance
                (getApplication().getResources().getString(R.string.dua_surah_base_url),
                        QuranApiData.class,new DuaDataDeserializer()).create(QuranApi.class);
    }

    public void fetchFromRemote(){
        makeMergeApiCalls(mQuranApi.getDua(12, "12:64"),
                mQuranApi.getDua(13, "13:8"));
        makeMergeApiCalls(mQuranApi.getDua(16, "16:127"),
                mQuranApi.getDua(16, "16:128"));
        makeSingleApiCall(mQuranApi.getSingleDua(25, "25:74"));
        makeSingleApiCall(mQuranApi.getSingleDua(2, "2:255"));
    }

    private void makeSingleApiCall(Single<QuranApiData> singleDua) {
        singleDua.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<QuranApiData>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull QuranApiData quranApiData) {
                        Log.d(Constants.ZIKR_TAG,TAG + " makeSingleApiCall: onSuccess: " + "surahnum " + quranApiData.getSurahNum()
                                + "versenum " + quranApiData.getVerseNum());
                        if (quranApiData.getSurahNum() == 2 && quranApiData.getVerseNum() == 255){
                            Log.d(Constants.ZIKR_TAG,TAG + " makeSingleApiCall: onSuccess: if");
                            insertDataToDb(new QuranDbData(getApplication().
                                    getResources().getString(R.string.ayat),quranApiData.getAyahText()));
                        }
                        else{
                            Log.d(Constants.ZIKR_TAG,TAG + " makeSingleApiCall: onSuccess: else");
                            insertDataToDb(new QuranDbData(getApplication().getResources().
                                    getString(R.string.duaName),quranApiData.getAyahText()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.ZIKR_TAG,TAG + "makeSingleApiCall: onError: " + e.getLocalizedMessage());
                    }
                });
    }

    private void makeMergeApiCalls(Observable<QuranApiData> firstApiCall,
                                   Observable<QuranApiData> secondApiCall) {
        Log.d(Constants.ZIKR_TAG,TAG +  "makeMergeApiCalls:");
        Observable.merge(firstApiCall,secondApiCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QuranApiData>() {
                    String textData = "";
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull QuranApiData quranApiData) {
                        Log.d(Constants.ZIKR_TAG,TAG + " makeMergeApiCalls: onSuccess: " +
                                "surahnum " + quranApiData.getSurahNum()
                                + "versenum " + quranApiData.getVerseNum());
                        textData = textData == "" ? textData + quranApiData.getAyahText() :
                                textData + " - " + quranApiData.getAyahText();
                        if (textData.startsWith(getApplication().getResources().getString(R.string.dua_excluded))){
                            Log.d(Constants.ZIKR_TAG,TAG + " makeMergeApiCalls: onNext: " + "inside if" +
                                    getApplication().getResources().getString(R.string.dua_excluded));
                            textData = textData.replaceAll(getApplication().getResources().getString(R.string.dua_excluded),"");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.ZIKR_TAG,TAG + "makeMergeApiCalls: onError: " +
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(Constants.ZIKR_TAG,TAG +  " makeMergeApiCalls: onComplete: " + textData);
                        insertDataToDb(new QuranDbData(getApplication().getResources().
                                getString(R.string.duaName),textData));
                    }
                });
    }

    private void insertDataToDb(QuranDbData quranDbData) {
        Completable completable = QuranDatabase.getInstance(getApplication()).quranDao().insert(quranDbData);
        completable.subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(Constants.ZIKR_TAG,TAG +  " insertDataToDb:onComplete: ");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.d(Constants.ZIKR_TAG,TAG +  " insertDataToDb : onError: "
                                + e.getLocalizedMessage());
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}





