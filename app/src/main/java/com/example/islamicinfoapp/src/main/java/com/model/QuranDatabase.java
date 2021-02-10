package com.example.islamicinfoapp.src.main.java.com.model;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {QuranDbData.class,SurahData.class,PrayerTiming.class},version = 1)
public abstract class QuranDatabase extends RoomDatabase {

    private static QuranDatabase mQuranDatabaseInstance;

    public static synchronized QuranDatabase getInstance(Context context){
        if (mQuranDatabaseInstance == null){
            mQuranDatabaseInstance = Room.databaseBuilder
                    (context,QuranDatabase.class,"QuranDatabase").build();
        }
        return mQuranDatabaseInstance;
    }
    public abstract QuranDao quranDao();
}
