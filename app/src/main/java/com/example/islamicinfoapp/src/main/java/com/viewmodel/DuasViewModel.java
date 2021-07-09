package com.example.islamicinfoapp.src.main.java.com.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.DuaApiData;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApi;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApiService;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDao;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDbData;
import com.example.islamicinfoapp.src.main.java.com.utilities.DuaDataDeserializer;

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

    public MutableLiveData<List<QuranDbData>> duasLiveData = new MutableLiveData<>();
    private QuranApi mQuranApi;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private AsyncTask<QuranDbData,Void,Void> task;
    private AsyncTask<String,Void, List<QuranDbData>> retrieveTask;
    private static final String TAG = DuasViewModel.class.getSimpleName();

    public DuasViewModel(@NonNull Application application) {
        super(application);
        mQuranApi = QuranApiService.getRetrofitInstance
                (getApplication().getResources().getString(R.string.dua_surah_base_url),
                        DuaApiData.class,new DuaDataDeserializer()).create(QuranApi.class);
    }

    public void fetchFromRemote(){
        makeMergeApiCalls(mQuranApi.getFirstDuaPartOne(),mQuranApi.getFirstDuaPartTwo());
        //makeMergeApiCalls(quranApi.getFirstDuasEnglishPartOne(), quranApi.getFirstDuasEnglishPartTwo());
        makeMergeApiCalls(mQuranApi.getSecondDuasPartOne(),mQuranApi.getSecondDuasPartTwo());
        //makeMergeApiCalls(quranApi.getSecondDuasEnglishPartOne(),quranApi.getSecondDuasEnglishPartTwo());
        makeSingleApiCall(mQuranApi.getThirdDuas());
        makeSingleApiCall(mQuranApi.getAyatuKursi());
        //makeSingleApiCall(quranApi.getThirdDuasEnglish());
    }

//    private void makeSingleApiCall(Single<DuaApiData> singleDua) {
//        singleDua.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<DuaApiData>() {
//                    @Override
//                    public void onSuccess(DuaApiData duaApiData) {
//                        Log.d(Constants.ZIKR_TAG,TAG + " onSuccess: " + duaApiData.getDataNumber());
//                        task = new DuaTask();
//                        if (duaApiData.getDataNumber() == 262){
//                            Log.d(Constants.ZIKR_TAG,TAG + " onSuccess: " + duaApiData.getDataNumber());
//                            task.execute(new QuranDbData(duaApiData.getText(),
//                                    getApplication().getResources().getString(R.string.ayat)));
//
//                        }
//                        else{
//                            task.execute(new QuranDbData(duaApiData.getText(),
//                                    getApplication().getResources().getString(R.string.duaName)));
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//    }
//
//
//    private void makeMergeApiCalls(Observable<DuaApiData> firstApiCall, Observable<DuaApiData> secondApiCall) {
//        Toast.makeText(getApplication(), "fetch from remote", Toast.LENGTH_SHORT).show();
//        //Observable<DuaApiData> firstDuaPartOne = quranApi.getFirstDuaPartOne();
//        //Observable<DuaApiData> firstDuaPartTwo = quranApi.getFirstDuaPartTwo();
//        Observable.merge(firstApiCall,secondApiCall)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<DuaApiData>() {
//                    String textData = "";
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(DuaApiData duaApiData) {
//                        Log.d(Constants.ZIKR_TAG,TAG +  " onNext: " + duaApiData.getDataNumber() + " " +
//                                duaApiData.getSurahName() + " " + duaApiData.getLanguage());
//                        textData = textData == "" ? textData + duaApiData.getText() : textData + " - " + duaApiData.getText();
//                        if (textData.startsWith(getApplication().getResources().getString(R.string.dua_excluded))){
//                            Log.d(Constants.ZIKR_TAG,TAG + " onNext: " + "inside if" +
//                                    getApplication().getResources().getString(R.string.dua_excluded));
//                            textData = textData.replaceAll(getApplication().getResources().getString(R.string.dua_excluded),"");
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
//                        //Toast.makeText(getApplication(), "oncomplete " + textData, Toast.LENGTH_SHORT).show();
//                        Log.d(Constants.ZIKR_TAG,TAG +  " onComplete: " + textData);
//                        task = new DuaTask();
//                        task.execute(new QuranDbData(textData,
//                                getApplication().getResources().getString(R.string.duaName)));
//                    }
//                });
//    }
//
//    public void fetchFromDatabase(String name){
//        retrieveTask = new RetrieveTask();
//        retrieveTask.execute(name);
//    }
//
//    private class DuaTask extends AsyncTask<QuranDbData,Void,Void> {
//        @Override
//        protected Void doInBackground(QuranDbData... quranDbData) {
//            QuranDbData quranDbData1 = quranDbData[0];
//            QuranDao quranDao = QuranDatabase.getInstance(getApplication()).quranDao();
//            Long result = quranDao.insert(quranDbData1);
//            Log.d(Constants.ZIKR_TAG,TAG + " doInBackground: " + result.toString());
//            return null;
//        }
//    }
//
//        private class RetrieveTask extends AsyncTask<String,Void,List<QuranDbData>>{
//            @Override
//            protected List<QuranDbData> doInBackground(String... strings) {
//                String name = strings[0];
//                Log.d(Constants.ZIKR_TAG,TAG+ " doInBackground: retreive" + name);
//                return QuranDatabase.getInstance(getApplication()).quranDao().selectAllDuas(name);
//            }
//
//            @Override
//            protected void onPostExecute(List<QuranDbData> quranDbData) {
//                Log.d(Constants.ZIKR_TAG, TAG + " onPostExecute: " + quranDbData.size());
//                if (quranDbData != null)
//                    duasLiveData.setValue(quranDbData);
//            }
//        }

    private void makeSingleApiCall(Single<DuaApiData> singleDua) {
        singleDua.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DuaApiData>() {
                    @Override
                    public void onSuccess(DuaApiData duaApiData) {
                        Log.d(Constants.ZIKR_TAG,TAG + " onSuccess: " + duaApiData.getDataNumber());
                        //task = new DuaTask();
                        if (duaApiData.getDataNumber() == 262){
                            Log.d(Constants.ZIKR_TAG,TAG + " onSuccess: if" + duaApiData.getDataNumber());
                            insertDataToDb(new QuranDbData(duaApiData.getText(),
                                    getApplication().getResources().getString(R.string.ayat)));

                        }
                        else{
                            Log.d(Constants.ZIKR_TAG,TAG + " onSuccess: else" + duaApiData.getDataNumber());
                            insertDataToDb(new QuranDbData(duaApiData.getText(),
                                    getApplication().getResources().getString(R.string.duaName)));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.ZIKR_TAG,TAG + " onError: " + e.getLocalizedMessage());
                    }
                });
    }

    private void makeMergeApiCalls(Observable<DuaApiData> firstApiCall, Observable<DuaApiData> secondApiCall) {
        Toast.makeText(getApplication(), "fetch from remote", Toast.LENGTH_SHORT).show();
        //Observable<DuaApiData> firstDuaPartOne = quranApi.getFirstDuaPartOne();
        //Observable<DuaApiData> firstDuaPartTwo = quranApi.getFirstDuaPartTwo();
        Observable.merge(firstApiCall,secondApiCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuaApiData>() {
                    String textData = "";
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DuaApiData duaApiData) {
                        Log.d(Constants.ZIKR_TAG,TAG +  " onNext: " + duaApiData.getDataNumber() + " " +
                                duaApiData.getSurahName() + " " + duaApiData.getLanguage());
                        textData = textData == "" ? textData + duaApiData.getText() : textData + " - " + duaApiData.getText();
                        if (textData.startsWith(getApplication().getResources().getString(R.string.dua_excluded))){
                            Log.d(Constants.ZIKR_TAG,TAG + " onNext: " + "inside if" +
                                    getApplication().getResources().getString(R.string.dua_excluded));
                            textData = textData.replaceAll(getApplication().getResources().getString(R.string.dua_excluded),"");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.ZIKR_TAG,TAG + " onError: " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        //Toast.makeText(getApplication(), "oncomplete " + textData, Toast.LENGTH_SHORT).show();
                        Log.d(Constants.ZIKR_TAG,TAG +  " onComplete: " + textData);
                        //task = new DuaTask();
                        insertDataToDb(new QuranDbData(textData,
                                getApplication().getResources().getString(R.string.duaName)));
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
                        Log.d(Constants.ZIKR_TAG,TAG +  " onComplete: ");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.d(Constants.ZIKR_TAG,TAG +  " onError: " + e.getLocalizedMessage());
                    }
                });
    }

//    public void fetchFromDatabase(String name){
//        retrieveTask = new RetrieveTask();
//        retrieveTask.execute(name);
//    }
//
//    private class RetrieveTask extends AsyncTask<String,Void,List<QuranDbData>>{
//        @Override
//        protected List<QuranDbData> doInBackground(String... strings) {
//            String name = strings[0];
//            Log.d(Constants.ZIKR_TAG,TAG+ " doInBackground: retreive" + name);
//            return QuranDatabase.getInstance(getApplication()).quranDao().selectAllDuas(name);
//        }
//
//        @Override
//        protected void onPostExecute(List<QuranDbData> quranDbData) {
//            Log.d(Constants.ZIKR_TAG, TAG + " onPostExecute: " + quranDbData.size());
//            if (quranDbData != null)
//                duasLiveData.setValue(quranDbData);
//        }
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //mCompositeDisposable.clear();
    }
}





