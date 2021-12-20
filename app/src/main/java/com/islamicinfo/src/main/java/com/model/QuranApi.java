package com.islamicinfo.src.main.java.com.model;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuranApi {

    @GET("imlaei?")
    Observable<QuranApiData> getDua(@Query("chapter_number") int chap_num,
               @Query("verse_key") String verse_key);

    @GET("imlaei?")
    Observable<SurahData> getSurah(@Query("chapter_number") int chap_num);

    @GET("imlaei?")
    Single<QuranApiData> getSingleDua(@Query("chapter_number") int chap_num,
                     @Query("verse_key") String verse_key);


    @GET("timingsByCity?")
    Observable<PrayerTiming> getPrayerTiming(@Query("city") String city,
                                          @Query("country") String country,@Query("method") int method,
                                             @Query("date_or_timestamp") String date);

    @GET("timingsByCity/{dateValue}?")
    Observable<Response<PrayerTiming>> getPrayerTimings(@Path("dateValue") String dateParam, @Query("city") String city,
                                                       @Query("country") String country, @Query("method") int method);

}
