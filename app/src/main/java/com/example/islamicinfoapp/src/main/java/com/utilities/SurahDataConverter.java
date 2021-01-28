package com.example.islamicinfoapp.src.main.java.com.utilities;

import androidx.room.TypeConverter;
import com.example.islamicinfoapp.src.main.java.com.model.SurahAyahItemApiData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SurahDataConverter {
    @TypeConverter
    public String fromSurahAyahItemApiDataList(List<SurahAyahItemApiData> surahAyahItemApiDatas){
        if (surahAyahItemApiDatas == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<SurahAyahItemApiData>>(){}.getType();
        String json = gson.toJson(surahAyahItemApiDatas,type);
        return json;
    }

    @TypeConverter
    public List<SurahAyahItemApiData> tosurahAyahItemApiDataList(String surahAyahItemApiDataString){
        if (surahAyahItemApiDataString == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<SurahAyahItemApiData>>(){}.getType();
        List<SurahAyahItemApiData> surahAyahItemApiDataList = gson.fromJson(surahAyahItemApiDataString,type);
        return surahAyahItemApiDataList;
    }
}
