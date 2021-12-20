package com.islamicinfo.src.main.java.com.utilities;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.model.QuranApiData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DuaDataDeserializer implements JsonDeserializer<QuranApiData> {
    private static final String TAG =  DuaDataDeserializer.class.getSimpleName();

    @Override
    public QuranApiData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        int id = 0,surahNum = 0,verseNum = 0;
        String text = "";
        JsonArray verses =json.getAsJsonObject().get("verses").getAsJsonArray();
        if (verses != null){
            Log.d(Constants.ZIKR_TAG,TAG +  " deserialize: " + verses.size());
            for (int i = 0; i<verses.size(); i++){
                JsonObject verse = verses.get(i).getAsJsonObject();
                id = verse.get("id").getAsInt();
                surahNum = Integer.parseInt(verse.get("verse_key").getAsString().split(":")[0]);
                verseNum = Integer.parseInt(verse.get("verse_key").getAsString().split(":")[1]);
                text = verse.get("text_imlaei").getAsString();
                Log.d(Constants.ZIKR_TAG,TAG +  " deserialize: " + "id" + id  + "surahnum" + surahNum
                + "verseNum" + verseNum + "text" + text);
            }
        }
        return new QuranApiData(id,surahNum,verseNum,text);
    }
}
