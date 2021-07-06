package com.example.islamicinfoapp.src.main.java.com.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import io.reactivex.Completable;

@Dao
public interface QuranDao {

    @Insert
    Long insert(QuranDbData quranDbData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(SurahData surahData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PrayerTiming prayerTiming);

    @Query("SELECT * FROM QuranDbData WHERE name = :name")
    List<QuranDbData> selectAllDuas(String name);

     @Query("SELECT * FROM SurahData")
    List<SurahData> getAllSurahs();

    @Query("SELECT * FROM SurahData WHERE surahNameEnglish =:surahNameEnglish")
    LiveData<SurahData> getSurahData(String surahNameEnglish);

    @Query("DELETE FROM QuranDbData where name =:name")
    void deleteAllQuranData(String name);

    @Query("DELETE FROM QuranDbData")
    void deleteAllQuranData();

    @Query("DELETE FROM SurahData")
    void deleteAllSurahData();

    @Query("SELECT * FROM PrayerTiming WHERE city =:city AND country=:country AND prayerTimeEngDate=:date")
    LiveData<PrayerTiming> getPrayerTimingOfCity(String city, String country, String date);

    @Query("DELETE FROM PrayerTiming")
    void deleteAllPrayerTimingData();

    @Query("DELETE FROM PrayerTiming WHERE DateTime(prayerTimeEngDate) < DateTime(:date)")
    Completable deletePrayerTimeData(String date);

    @Query("SELECT * FROM PrayerTiming WHERE date(prayerTimeEngDate) < date(:date)")
    LiveData<PrayerTiming> getAllOldRecords(String date);

    @Query("DELETE FROM PrayerTiming WHERE city!=:city")
    Completable deleteLocationData(String city);

    @Query("SELECT * FROM PrayerTiming WHERE city=:city AND country=:country AND prayerTimeEngDate=:todaysdate")
    LiveData<PrayerTiming> getRecordForToday(String city,String country,String todaysdate);

    @Query("SELECT COUNT(*) FROM PrayerTiming WHERE city=:city AND country=:country AND prayerTimeEngDate=:date")
    LiveData<Integer> getRecordCount(String city, String country, String date);

    @Query("SELECT COUNT(*) FROM SurahData")
    LiveData<Integer> getSurahDataCount();

    @Query("SELECT COUNT(*) FROM QuranDbData")
    LiveData<Integer> getQuranDataCount();

//    @Query("SELECT EXISTS(SELECT * FROM PrayerTiming WHERE city=:city AND country=:country AND prayerTimeEngDate=:date)")
//    boolean isExists(String city, String country, String date);


//    @Insert
//    Long insert(DuaApiData quraData);
//
//    @Query("SELECT * FROM DuaApiData")
//    List<DuaApiData> getAllQuranData();

//    @Query("SELECT * FROM DuaApiData WHERE uuid = :uuid")
//    DuaApiData getQuranData(int uuid);

//    @Query("SELECT * FROM DuaApiData WHERE  =:identifier" )
//     List<QData> getTypeQuranData(String identifier);

//    @Query("DELETE FROM DuaApiData WHERE identifier = :identifier")
//    void deleteTypeQuranData(String identifier);
}
