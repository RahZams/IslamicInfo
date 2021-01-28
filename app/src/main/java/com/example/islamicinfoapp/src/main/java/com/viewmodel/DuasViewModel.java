package com.example.islamicinfoapp.src.main.java.com.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.DuaApiData;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApi;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApiService;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDao;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDbData;
import com.example.islamicinfoapp.src.main.java.com.utilities.DuaDataDeserializer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DuasViewModel extends AndroidViewModel {

    public MutableLiveData<List<QuranDbData>> duasLiveData = new MutableLiveData<>();
    private QuranApi quranApi;
    private AsyncTask<QuranDbData,Void,Void> task;
    private AsyncTask<String,Void, List<QuranDbData>> retrieveTask;
    public DuasViewModel(@NonNull Application application) {
        super(application);
        quranApi = QuranApiService.getRetrofitInstance
                (getApplication().getResources().getString(R.string.dua_surah_base_url),
                        DuaApiData.class,new DuaDataDeserializer()).create(QuranApi.class);
    }

    public void fetchFromRemote(){
        makeMergeApiCalls(quranApi.getFirstDuaPartOne(),quranApi.getFirstDuaPartTwo());
        //makeMergeApiCalls(quranApi.getFirstDuasEnglishPartOne(), quranApi.getFirstDuasEnglishPartTwo());
        makeMergeApiCalls(quranApi.getSecondDuasPartOne(),quranApi.getSecondDuasPartTwo());
        //makeMergeApiCalls(quranApi.getSecondDuasEnglishPartOne(),quranApi.getSecondDuasEnglishPartTwo());
        makeSingleApiCall(quranApi.getThirdDuas());
        makeSingleApiCall(quranApi.getAyatuKursi());
        //makeSingleApiCall(quranApi.getThirdDuasEnglish());
    }

    private void makeSingleApiCall(Single<DuaApiData> singleDua) {
        singleDua.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DuaApiData>() {
                    @Override
                    public void onSuccess(DuaApiData duaApiData) {
                        Log.d("tag", "onSuccess: " + duaApiData.getDataNumber());
                        task = new DuaTask();
                        if (duaApiData.getDataNumber() == 262){
                            Log.d("ayat", "onSuccess: ");
                            task.execute(new QuranDbData(duaApiData.getText(),
                                    getApplication().getResources().getString(R.string.ayat)));

                        }
                        else{
                            task.execute(new QuranDbData(duaApiData.getText(),
                                    getApplication().getResources().getString(R.string.duaName)));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

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
                        Toast.makeText(getApplication(), "number" + duaApiData.getDataNumber(), Toast.LENGTH_LONG).show();
                        //Log.d("tag", "onNext: " + duaApiData.getDataNumber() + " " + duaApiData.getSurahName() + " " + duaApiData.getLanguage());
                        textData = textData == "" ? textData + duaApiData.getText() : textData + " - " + duaApiData.getText();
                        if (textData.startsWith(getApplication().getResources().getString(R.string.dua_excluded))){
                            Log.d("tag", "onNext: " + "inside if" +
                                    getApplication().getResources().getString(R.string.dua_excluded));
                            textData = textData.replaceAll(getApplication().getResources().getString(R.string.dua_excluded),"");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //Toast.makeText(getApplication(), "oncomplete " + textData, Toast.LENGTH_SHORT).show();
                        Log.d("tag", "onComplete: " + textData);
                        task = new DuaTask();
                        task.execute(new QuranDbData(textData,
                                getApplication().getResources().getString(R.string.duaName)));
                    }
                });
    }

    public void fetchFromDatabase(String name){
        retrieveTask = new RetrieveTask();
        retrieveTask.execute(name);
    }

    private class DuaTask extends AsyncTask<QuranDbData,Void,Void> {
        @Override
        protected Void doInBackground(QuranDbData... quranDbData) {
            QuranDbData quranDbData1 = quranDbData[0];
            QuranDao quranDao = QuranDatabase.getInstance(getApplication()).quranDao();
            Long result = quranDao.insert(quranDbData1);
            Log.d("tag", "doInBackground: " + result.toString());
            return null;
        }
    }

        private class RetrieveTask extends AsyncTask<String,Void,List<QuranDbData>>{
            @Override
            protected List<QuranDbData> doInBackground(String... strings) {
                String name = strings[0];
                Log.d("tag", "doInBackground: retreive" + name);
                return QuranDatabase.getInstance(getApplication()).quranDao().selectAllDuas(name);
            }

            @Override
            protected void onPostExecute(List<QuranDbData> quranDbData) {
                Log.d("tag", "onPostExecute: " + quranDbData.size());
                if (quranDbData != null)
                    duasLiveData.setValue(quranDbData);
            }
        }
    }





