package com.islamicinfo.src.main.java.com.viewmodel;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.model.QuranApi;
import com.islamicinfo.src.main.java.com.model.QuranApiService;
import com.islamicinfo.src.main.java.com.model.QuranDatabase;
import com.islamicinfo.src.main.java.com.model.SurahData;
import com.islamicinfo.src.main.java.com.utilities.SurahDataDeserializer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SurahViewModel extends AndroidViewModel {
    public MutableLiveData<Boolean> isRemoteFetched = new MutableLiveData<>();
    private QuranApi mQuranApi;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private static final String TAG = SurahViewModel.class.getSimpleName();
    int count,surahCount;
    private HashMap<Integer, SurahData> data = new HashMap<>();


    public SurahViewModel(@NonNull Application application) {
        super(application);
        mQuranApi = QuranApiService.getRetrofitInstance(getApplication().getResources().
                        getString(R.string.dua_surah_base_url), SurahData.class, new SurahDataDeserializer())
                .create(QuranApi.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertSurahToDb(List<SurahData> allData){
        //Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb:" + allData.get(1).toString());
        allData.forEach(surahData -> {
            Log.d(Constants.SURAH_TAG, TAG + "insertSurahToDb: " + "surahnum " + surahData.getSurahNum() +
                    "text " + surahData.getSurahAyahsText());
        });
        Completable completable = QuranDatabase.getInstance(getApplication()).
                quranDao().insertAllSurah(allData);
        mCompositeDisposable.add(
                completable.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(
                                ()->{
                                    //This is on Complete
                                    Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb: Completed");
                                    isRemoteFetched.postValue(true);
                                },
                                error->{
                                    Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb: " + "onError: message:"
                                            + error.getLocalizedMessage() + " stacktrace:" + error.getStackTrace().toString() +
                                            " cause: " + error.getCause() + error.getMessage());
                                }
                        )
        );
    }

    public void insertSingleSurahToDb(SurahData surah){
        //Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb:" + allData.get(1).toString());

        Completable completable = QuranDatabase.getInstance(getApplication()).
                quranDao().insert(surah);
        mCompositeDisposable.add(
                completable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(
                        ()->{
                            //This is on Complete
                            Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb: Completed");
                            //isRemoteFetched.postValue(true);
                            },
                        error->{
                            Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb: " + "onError: message:"
                                    + error.getLocalizedMessage() + " stacktrace:" + error.getStackTrace().toString() +
                                    " cause: " + error.getCause() + error.getMessage());
                            }
                        )
        );
    }

    public void fetchFromRemote(){
        //ArrayList<SurahData> allSurahs = new ArrayList<>();
        ArrayList<Observable<SurahData>> allSurahObservables = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Constants.surah.forEach((key,value)->{
                allSurahObservables.add(mQuranApi.getSurah(value));
            });
        }
        Observable<SurahData> mergedObservable = Observable.merge(allSurahObservables);
        mCompositeDisposable.add(
                mergedObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                //.debounce(0, TimeUnit.MILLISECONDS)
                .subscribe(
                        item -> {
                            //This is onNext
                            Log.d("ZAK","OnNext: Item:"+item.toString());
                            insertSingleSurahToDb(item);
                            //allSurahs.add(item);
                            //data.put(zCount, item);
                            //Log.d("ZAK","List Item "+zCount+": "+allSurahs.get(zCount++));
                            /*if(zCount==27){
                                Log.d("ZAK","ITERATING FULL LIST");
                                data.forEach((num,surah) -> {
                                    Log.d("ZAK",surah.toString());
                                });
                            }*/
                            //Log.d(Constants.SURAH_TAG, TAG  + " fetchFromRemote: onNext:item " +
                                    //"surahNum" + item.getSurahNum()+ " surahData:"+item.toString());
//                              +  "surah text" + surahData.getSurahAyahsText().toString());
                            //surahCount = surahCount + 1;
//                            Log.d(Constants.SURAH_TAG, TAG  + " fetchFromRemote: onNext:surah " +
//                                    "surahCount " + surahCount);
                            /*allSurahs.forEach(surahData -> {
                                Log.d(Constants.SURAH_TAG, TAG  + " fetchFromRemote: onNext: allSurahs" +
                                        surahData.getSurahNum() +
                                        surahData.getSurahAyahsText());
                            });*/
                            //insertDataToDb(item);
                        },
                        error->{
                            //This is onError
                            Log.d(Constants.SURAH_TAG, TAG  + " fetchFromRemote: onError: " +
                                    "message" + error.getMessage() + "cause" + error.getCause());
                        },
                        ()->{
                            //This is onComplete
                            Log.d("ZAK","All data emitted successfully.. In OnComplete");
                            /*for (int i=0;i<allSurahs.size();i++){
                                Log.d("ZAK","allSuras OnComplete:"+allSurahs.get(i).toString());
                            }*/
                            isRemoteFetched.postValue(true);
                            //allSurahs.forEach(item -> {
                                /*Log.d(Constants.SURAH_TAG, TAG  + " fetchFromRemote: onComplete" +
                                        surahData.getSurahAyahsText());*/
                              //  Log.d("ZAK","allSuras OnComplete:"+item.toString());
                            //});
                            //insertSurahToDb(allSurahs);
                            //isRemoteFetched.setValue(true);
                        }
                )
        );
    }

    /*public void fetchFromRemote111() {
        count = 0;
        surahCount = 0;
        Log.d(Constants.SURAH_TAG, TAG + " fetchFromRemote: ");
        makeMergeCalls(mQuranApi.getSurah(31),mQuranApi.getSurah(12),mQuranApi.getSurah(19),
                mQuranApi.getSurah(2),mQuranApi.getSurah(10),mQuranApi.getSurah(37));
        makeMergeCalls(mQuranApi.getSurah(67),mQuranApi.getSurah(3),mQuranApi.getSurah(76),
                mQuranApi.getSurah(97),mQuranApi.getSurah(108),mQuranApi.getSurah(48));
//        makeMergeCalls(mQuranApi.getSurah(110),mQuranApi.getSurah(56),mQuranApi.getSurah(95),
//                mQuranApi.getSurah(16),mQuranApi.getSurah(36),mQuranApi.getSurah(112));
//        makeMergeCalls(mQuranApi.getSurah(97),mQuranApi.getSurah(95),mQuranApi.getSurah(25),
//               mQuranApi.getSurah(47),mQuranApi.getSurah(103),mQuranApi.getSurah(51));
//        makeMergeCalls(mQuranApi.getSurah(22),mQuranApi.getSurah(35),mQuranApi.getSurah(78),
//                mQuranApi.getSurah(114),mQuranApi.getSurah(113),mQuranApi.getSurah(109));
    }

    private void makeThreadSleep() {
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void makeMergeCalls(Observable<SurahData> surahOne, Observable<SurahData> surahTwo,
                                Observable<SurahData> surahThree, Observable<SurahData> surahFour,
                                Observable<SurahData> surahFive, Observable<SurahData> surahSix) {
        Log.d(Constants.SURAH_TAG, TAG  + " makeMergeCalls: " + "surahOne" + surahOne + "surahTwo" + surahTwo
        + "surahThree" + surahThree + "surahFour" + surahFour + "surahFive" + surahFive + "surahSix" + surahSix);

        //makeThreadSleep();
        Observable.merge(Arrays.asList(surahOne, surahTwo, surahThree, surahFour, surahFive, surahSix))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SurahData>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull SurahData surahData) {
                        Log.d(Constants.SURAH_TAG, TAG  + " makeMergeCalls: onNext:surah " +
                                        "surahNum" + surahData.getSurahNum());
//                              +  "surah text" + surahData.getSurahAyahsText().toString());
                        surahCount = surahCount + 1;
                        Log.d(Constants.SURAH_TAG, TAG  + " makeMergeCalls: onNext:surah " +
                                "surahCount " + surahCount);
                        insertDataToDb(surahData);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.d(Constants.SURAH_TAG, TAG  + " makeMergeCalls: onError: " +
                                                       "message" + e.getMessage() + "cause" + e.getCause());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(Constants.SURAH_TAG, TAG  + " makeMergeCalls: " +
                                "onComplete: surahCount" + surahCount);
                    }
                });
    }

    private void insertDataToDb(SurahData surahData) {
        Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb: " + surahData.getSurahNum());

        Completable completable = QuranDatabase.getInstance(getApplication()).quranDao().insert(surahData);
        completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb: " + " onComplete: ");
                count = count + 1;
                Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb: " + " onComplete: count" + count);
                if(count == Integer.parseInt(getApplication().getString(R.string.surah_total_count)))
                {
                    isRemoteFetched.postValue(true);
                }
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.d(Constants.SURAH_TAG, TAG + " insertDataToDb: " + "onError: message:"
                        + e.getLocalizedMessage() + " stacktrace:" + e.getStackTrace().toString() +
                        " cause: " + e.getCause() + e.getMessage());
            }
        });
    }*/

    @Override
    protected void onCleared() {
        super.onCleared();
        count = 0;
        mCompositeDisposable.clear();
    }
}
