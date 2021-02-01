package com.example.islamicinfoapp.src.main.java.com.model;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuranApi {

//    @GET("12:64")
//    QData getFirstDuaPartOne();
//
//    @GET("13:8")
//    QData getFirstDuaPartTwo();
//
//    @GET("12:64/en.asad")
//    QData getFirstDuasEnglishPartOne();
//
//    @GET("13:8/en.asad")
//    QData getFirstDuasEnglishPartTwo();
//
//    @GET("16:127")
//    QData getSecondDuasPartOne();
//
//    @GET("16:128")
//    QData getSecondDuasPartTwo();
//
//    @GET("16:127/en.asad")
//    QData getSecondDuasEnglishPartOne();
//
//    @GET("16:128/en.asad")
//    QData getSecondDuasEnglishPartTwo();
//
//    @GET("25:74")
//    QData getThirdDuas();
//
//    @GET("25:74/en.asad")
//    QData getThirdDuasEnglish();

    @GET("ayah/12:64")
    Observable<DuaApiData> getFirstDuaPartOne();

    @GET("ayah/13:8")
    Observable<DuaApiData> getFirstDuaPartTwo();

    @GET("ayah/12:64/en.asad")
    Observable<DuaApiData> getFirstDuasEnglishPartOne();

    @GET("ayah/13:8/en.asad")
    Observable<DuaApiData> getFirstDuasEnglishPartTwo();

    @GET("ayah/16:127")
    Observable<DuaApiData> getSecondDuasPartOne();

    @GET("ayah/16:128")
    Observable<DuaApiData> getSecondDuasPartTwo();

    @GET("ayah/16:127/en.asad")
    Observable<DuaApiData> getSecondDuasEnglishPartOne();

    @GET("ayah/16:128/en.asad")
    Observable<DuaApiData> getSecondDuasEnglishPartTwo();

    @GET("ayah/25:74")
    Single<DuaApiData> getThirdDuas();

    @GET("ayah/25:74/en.asad")
    Single<DuaApiData> getThirdDuasEnglish();

    @GET("ayah/262")
    Single<DuaApiData> getAyatuKursi();

    @GET("surah/36")
    Observable<SurahData> getSurahYaseen();

    @GET("surah/37")
    Observable<SurahData> getSurahAssaaffaat();

    @GET("surah/67")
    Observable<SurahData> getSurahMulk();

    @GET("surah/3")
    Observable<SurahData> getSurahImraan();

    @GET("surah/76")
    Observable<SurahData> getSurahInsaan();

    @GET("surah/97")
    Observable<SurahData> getSurahQadr();

    @GET("surah/108")
    Observable<SurahData> getSurahKauthar();

    @GET("surah/48")
    Observable<SurahData> getSurahFath();

    @GET("surah/110")
    Observable<SurahData> getSurahNasr();

    @GET("surah/56")
    Observable<SurahData> getSurahWaaqia();

    @GET("surah/95")
    Observable<SurahData> getSurahAttin();

    @GET("surah/16")
    Observable<SurahData> getSurahNahl();

    @GET("surah/112")
    Observable<SurahData> getSurahIkhlaas();

    @GET("surah/25")
    Observable<SurahData> getSurahFurqaan();

    @GET("surah/47")
    Observable<SurahData> getSurahMuhammad();

    @GET("surah/103")
    Observable<SurahData> getSurahAsr();

    @GET("surah/51")
    Observable<SurahData> getSurahDhaariyat();

    @GET("surah/22")
    Observable<SurahData> getSurahHajj();

    @GET("surah/35")
    Observable<SurahData> getSurahFaatir();

    @GET("surah/10")
    Observable<SurahData> getSurahYunus();

    @GET("surah/31")
    Observable<SurahData> getSurahLuqman();

    @GET("surah/19")
    Observable<SurahData> getSurahMaryam();

    @GET("surah/12")
    Observable<SurahData> getSurahYusuf();

    @GET("surah/2")
    Observable<SurahData> getSurahBaqara();

    @GET("surah/78")
    Observable<SurahData> getSurahNaba();

    @GET("surah/109")
    Observable<SurahData> getSurahKaafiroon();

    @GET("surah/114")
    Observable<SurahData> getSurahNaas();

    @GET("surah/113")
    Observable<SurahData> getSurahFalaq();

    @GET("surah/1")
    Observable<SurahData> getSurahFaatiha();

    @GET("surah/106")
    Observable<SurahData> getSurahQuraish();

    @GET("timingsByCity?")
    Single<PrayerTiming> getPrayerTiming(@Query("city") String city,
                                          @Query("country") String country,@Query("method") int method);

}
