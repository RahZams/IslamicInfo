package com.example.islamicinfoapp.src.main.java.com.model;

import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import io.reactivex.Observable;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuranApiService {

    public static Retrofit getRetrofitInstance(String url,Type type,Object typeAdapter) {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(gsonConverter(type,typeAdapter))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static Converter.Factory gsonConverter(Type type, Object typeAdapter) {

        return GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(type,typeAdapter).create());
    }

//    public Observable<DuaApiData> getFirstDuaPartOne() {
//        return mQuranApi.getFirstDuaPartOne();
//    }
//
//    public Observable<DuaApiData> getFirstDuaPartTwo() {
//        return mQuranApi.getFirstDuaPartTwo();
//    }
//
//    public Observable<DuaApiData> getFirstDuasEnglishPartOne() {
//        return mQuranApi.getFirstDuasEnglishPartOne();
//    }
//
//    public Observable<DuaApiData> getFirstDuasEnglishPartTwo() {
//        return mQuranApi.getFirstDuasEnglishPartTwo();
//    }
//
//    public Observable<DuaApiData> getSecondDuasPartOne() {
//        return mQuranApi.getSecondDuasPartOne();
//    }
//
//    public Observable<DuaApiData> getSecondDuasPartTwo() {
//        return mQuranApi.getSecondDuasPartTwo();
//    }
//
//    public Observable<DuaApiData> getSecondDuasEnglishPartOne() {
//        return mQuranApi.getSecondDuasEnglishPartOne();
//    }
//
//    public Observable<DuaApiData> getSecondDuasEnglishPartTwo() {
//        return mQuranApi.getSecondDuasEnglishPartTwo();
//    }
//
//    public Observable<DuaApiData> getThirdDuas() {
//        return mQuranApi.getThirdDuas();
//    }
//
//    public Observable<DuaApiData> getThirdDuasEnglish() {
//        return mQuranApi.getThirdDuasEnglish();
//    }


}
