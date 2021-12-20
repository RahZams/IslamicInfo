package com.islamicinfo.src.main.java.com.utilities;

import android.util.Log;

import androidx.room.TypeConverter;

import com.islamicinfo.src.main.java.com.model.QuranApiData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SurahDataConverter {
    private static final String TAG = SurahDataConverter.class.getSimpleName();

    @TypeConverter
    public static String fromSurahAyahItemApiDataList(List<QuranApiData> quranApiDbDatas){
        if (quranApiDbDatas == null){
            return null;
        }
        Log.d(TAG, "fromSurahAyahItemApiDataList: " + quranApiDbDatas.get(0).getAyahText());
        Gson gson = new Gson();
        Type type = new TypeToken<List<QuranApiData>>(){}.getType();
        String json = gson.toJson(quranApiDbDatas,type);
        Log.d(TAG, "fromSurahAyahItemApiDataList: " + json.toString());
        return json;
    }

    @TypeConverter
    public static List<QuranApiData> tosurahAyahItemApiDataList(String surahAyahString){
        if (surahAyahString == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<QuranApiData>>(){}.getType();
        List<QuranApiData> surahAyahItemApiDataList = gson.fromJson(surahAyahString,type);
        return surahAyahItemApiDataList;
    }
}
