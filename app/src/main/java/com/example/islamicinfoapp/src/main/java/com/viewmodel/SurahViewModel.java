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
import com.example.islamicinfoapp.src.main.java.com.model.QuranApi;
import com.example.islamicinfoapp.src.main.java.com.model.QuranApiService;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.model.SurahData;
import com.example.islamicinfoapp.src.main.java.com.utilities.SurahDataDeserializer;

import java.util.Arrays;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SurahViewModel extends AndroidViewModel {
    //public MutableLiveData<SurahData> mSurahDataMutableLiveData = new MutableLiveData<>();
    private QuranApi mQuranApi;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private static final String TAG = SurahViewModel.class.getSimpleName();
    //private SurahSaveToDBTask mSurahSaveToDBTask;
    //private SurahRetrieveTask mSurahRetrieveTask;

    public SurahViewModel(@NonNull Application application) {
        super(application);
        mQuranApi = QuranApiService.getRetrofitInstance(getApplication().getResources().getString(R.string.dua_surah_base_url),
                SurahData.class, new SurahDataDeserializer())
                .create(QuranApi.class);
    }

    public void fetchFromRemote() {
        Log.d(Constants.SURAH_TAG, TAG + " fetchFromRemote: ");
//        makeMergeCalls(mQuranApi.getSurahYusuf(),mQuranApi.getSurahYunus(),mQuranApi.getSurahYaseen()
//        ,mQuranApi.getSurahWaaqia(),mQuranApi.getSurahQadr(),mQuranApi.getSurahNasr(),mQuranApi.getSurahNahl()
//        ,mQuranApi.getSurahMulk(),mQuranApi.getSurahMuhammad(),mQuranApi.getSurahMaryam(),mQuranApi.getSurahLuqman()
//        ,mQuranApi.getSurahKauthar(),mQuranApi.getSurahInsaan(),mQuranApi.getSurahImraan(),mQuranApi.getSurahIkhlaas()
//        ,mQuranApi.getSurahHajj(),mQuranApi.getSurahFurqaan(),mQuranApi.getSurahFath(),mQuranApi.getSurahFaatir()
//        ,mQuranApi.getSurahDhaariyat(),mQuranApi.getSurahBaqara(),
//                mQuranApi.getSurahAttin(),mQuranApi.getSurahAssaaffaat(),mQuranApi.getSurahAsr());

        makeMergeCalls(mQuranApi.getSurahYusuf(), mQuranApi.getSurahYunus(), mQuranApi.getSurahYaseen()
                , mQuranApi.getSurahWaaqia(), mQuranApi.getSurahQadr(), mQuranApi.getSurahNasr());
        makeMergeCalls(mQuranApi.getSurahNahl(), mQuranApi.getSurahMulk(), mQuranApi.getSurahMuhammad(),
                mQuranApi.getSurahMaryam(), mQuranApi.getSurahLuqman(), mQuranApi.getSurahKauthar());
        makeMergeCalls(mQuranApi.getSurahInsaan(), mQuranApi.getSurahImraan(), mQuranApi.getSurahIkhlaas()
                , mQuranApi.getSurahHajj(), mQuranApi.getSurahFurqaan(), mQuranApi.getSurahFath());
        makeMergeCalls(mQuranApi.getSurahFaatir(), mQuranApi.getSurahDhaariyat(), mQuranApi.getSurahBaqara(),
                mQuranApi.getSurahAttin(), mQuranApi.getSurahAssaaffaat(), mQuranApi.getSurahAsr());
        makeMergeCalls(mQuranApi.getSurahNaba(),mQuranApi.getSurahKaafiroon(),mQuranApi.getSurahNaas(),
                mQuranApi.getSurahFalaq(),mQuranApi.getSurahFaatiha(),mQuranApi.getSurahQuraish());

//        Call<SurahData> call = mQuranApi.getSurahMaryam();
//        call.enqueue(new Callback<SurahData>() {
//            @Override
//            public void onResponse(Call<SurahData> call, Response<SurahData> response) {
//                Log.d("surah", "onResponse: " + response.body().getDataNumber());
//            }
//
//            @Override
//            public void onFailure(Call<SurahData> call, Throwable t) {
//                Log.d("surah", "onFailure: " + t.toString());
//            }
//        });
    }

    private void makeMergeCalls(Observable<SurahData> surahOne, Observable<SurahData> surahTwo,
                                Observable<SurahData> surahThree, Observable<SurahData> surahFour,
                                Observable<SurahData> surahFive, Observable<SurahData> surahSix) {
        Log.d(Constants.SURAH_TAG, TAG + " makeMergeCalls: " + surahOne);
        Observable.merge(Arrays.asList(surahOne, surahTwo, surahThree, surahFour, surahFive, surahSix))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<SurahData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SurahData surahData) {
                        //Toast.makeText(getApplication(), " on next surah " , Toast.LENGTH_SHORT).show();
                        Log.d(Constants.SURAH_TAG, TAG + " onNext:surah " + surahData.getDataNumber()
                                + " " + surahData.getSurahNameEnglish());
//                        mSurahSaveToDBTask = new SurahSaveToDBTask();
//                        mSurahSaveToDBTask.execute(surahData);
                        insertDataToDb(surahData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.SURAH_TAG, TAG +" onError: " + e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void insertDataToDb(SurahData surahData) {
        Log.d(Constants.SURAH_TAG, "insertDataToDb: " + surahData.getDataNumber());

        Completable completable = QuranDatabase.getInstance(getApplication()).quranDao().insert(surahData);
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                Log.d(Constants.SURAH_TAG, "onComplete: ");
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.d(Constants.SURAH_TAG, "onError: " + e.getLocalizedMessage() + " " + e.getCause());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //mCompositeDisposable.clear();
    }

    //    public void fetchFromDatabase(String surahName) {
//        mSurahRetrieveTask = new SurahRetrieveTask();
//        //mSurahRetrieveTask.execute(surahName);
//
//    }

//    private class SurahSaveToDBTask extends AsyncTask<SurahData, Void, Void> {
//
//        @Override
//        protected Void doInBackground(SurahData... surahData) {
//            SurahData mSurahData = surahData[0];
//            Long result = QuranDatabase.getInstance(getApplication()).quranDao().insert(mSurahData);
//            return null;
//        }
//    }

//    private class SurahRetrieveTask extends AsyncTask<String, Void, SurahData> {
//
//        @Override
//        protected SurahData doInBackground(String... strings) {
//            String surahName = strings[0];
//            Log.d("surah", "doInBackground: " + surahName);
//            //SurahData surahData = QuranDatabase.getInstance(getApplication()).quranDao().getSurahData(surahName);
////            if (surahData != null)
////                return surahData;
////            else
//                return null;
//        }
//
//        @Override
//        protected void onPostExecute(SurahData surahData) {
//            //Log.d("surah", "onPostExecute:SurahRetrieveTask " + surahData.getDataNumber());
//            if (surahData != null)
//                mSurahDataMutableLiveData.setValue(surahData);
//        }
//    }


//    private void makeMergeCalls(Observable<SurahData> surahYusuf, Observable<SurahData> surahYunus,
//                                Observable<SurahData> surahYaseen, Observable<SurahData> surahWaaqia,
//                                Observable<SurahData> surahQadr, Observable<SurahData> surahNasr,
//                                Observable<SurahData> surahNahl, Observable<SurahData> surahMulk,
//                                Observable<SurahData> surahMuhammad, Observable<SurahData> surahMaryam,
//                                Observable<SurahData> surahLuqman, Observable<SurahData> surahKauthar,
//                                Observable<SurahData> surahInsaan, Observable<SurahData> surahImraan,
//                                Observable<SurahData> surahIkhlaas, Observable<SurahData> surahHajj,
//                                Observable<SurahData> surahIkhlaas, Observable<SurahData> surahHajj,
//                                Observable<SurahData> surahFurqaan, Observable<SurahData> surahFath,
//                                Observable<SurahData> surahFaatir, Observable<SurahData> surahDhaariyat,
//                                Observable<SurahData> surahBaqara, Observable<SurahData> surahAttin,
//                                Observable<SurahData> surahAssaaffaat, Observable<SurahData> surahAsr) {
//        Observable.merge(Arrays.asList(surahYusuf,surahYunus,surahYaseen,surahWaaqia,surahQadr,surahNasr,surahNahl,
//                surahMulk,surahMuhammad,surahMaryam,surahLuqman,surahKauthar,surahInsaan,surahImraan,surahIkhlaas,
//                surahFurqaan,surahHajj,surahFath,surahFaatir,surahDhaariyat,surahBaqara,surahAttin,surahAssaaffaat,surahAsr))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<SurahData>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(SurahData surahData) {
//                        Toast.makeText(getApplication(), " on next surah " , Toast.LENGTH_SHORT).show();
//                        Log.d("surah", "onNext:surah " + surahData.getDataNumber() + " " + surahData.getSurahNameEnglish());
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
//    }


}
