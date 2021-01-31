package com.example.islamicinfoapp.src.main.java.com.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;

@Dao
public interface QuranDao {

    @Insert
    Long insert(QuranDbData quranDbData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SurahData surahData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PrayerTiming prayerTiming);

    @Query("SELECT * FROM QuranDbData WHERE name = :name")
    List<QuranDbData> selectAllDuas(String name);

     @Query("SELECT * FROM SurahData")
    List<SurahData> getAllSurahs();

    @Query("SELECT * FROM SurahData WHERE surahNameEnglish =:surahNameEnglish")
    SurahData getSurahData(String surahNameEnglish);

    @Query("DELETE FROM QuranDbData where name =:name")
    void deleteAllQuranData(String name);

    @Query("DELETE FROM QuranDbData")
    void deleteAllQuranData();

    @Query("DELETE FROM SurahData")
    void deleteAllSurahData();

    @Query("SELECT * FROM PrayerTiming WHERE city =:city AND country=:country AND prayerTimeEngDate=:date")
    PrayerTiming getPrayerTimingOfCity(String city, String country, String date);

    @Query("DELETE FROM PrayerTiming")
    void deleteAllPrayerTimingData();

    @Query("DELETE FROM PrayerTiming WHERE prayerTimeEngDate != :date")
    void deletePrayerTimeData(String date);

    @Query("SELECT COUNT(*) FROM PrayerTiming WHERE city=:city AND country=:country AND prayerTimeEngDate=:date")
    int getRecordCount(String city, String country, String date);

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
