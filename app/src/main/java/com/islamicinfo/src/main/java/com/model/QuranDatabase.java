package com.islamicinfo.src.main.java.com.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {QuranDbData.class,SurahData.class,PrayerTiming.class},version = 2)
public abstract class QuranDatabase extends RoomDatabase {

    private static QuranDatabase mQuranDatabaseInstance;

    public static synchronized QuranDatabase getInstance(Context context){
        if (mQuranDatabaseInstance == null){
            mQuranDatabaseInstance = Room.databaseBuilder
                    (context,QuranDatabase.class,"QuranDatabase")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return mQuranDatabaseInstance;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS QuranDbData");
            database.execSQL("DROP TABLE IF EXISTS SurahData");

//            database.execSQL("CREATE TABLE IF NOT EXISTS QuranDbData(rowid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL," +
//                    "quranText TEXT NOT NULL)");
            database.execSQL("CREATE TABLE IF NOT EXISTS QuranDbData(rowid INTEGER NOT NULL " +
                    "PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,quranText NOT NULL)");
            database.execSQL("CREATE TABLE IF NOT EXISTS SurahData(surahNum INTEGER NOT NULL " +
                    "PRIMARY KEY,surahAyahsText TEXT NOT NULL)");
        }
    };

    public abstract QuranDao quranDao();
}
