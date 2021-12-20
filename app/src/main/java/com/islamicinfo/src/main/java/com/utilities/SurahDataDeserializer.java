package com.islamicinfo.src.main.java.com.utilities;

import android.util.Log;

import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.model.QuranApiData;
import com.islamicinfo.src.main.java.com.model.SurahData;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SurahDataDeserializer implements JsonDeserializer {

    private List<QuranApiData> quranApiList = new ArrayList<>();
    private int surahNum;
    private static final String TAG = SurahDataDeserializer.class.getSimpleName();


    @Override
    public SurahData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonArray verses = json.getAsJsonObject().get("verses").getAsJsonArray();
        if (verses != null) {
            Log.d(Constants.ZIKR_TAG,TAG + " deserialize: " + "verses size " + verses.size());
            quranApiList.clear();
            for (int i = 0; i < verses.size(); i++) {
                JsonObject verse = verses.get(i).getAsJsonObject();
                int id = verse.get("id").getAsInt();
                surahNum = Integer.parseInt(verse.get("verse_key").getAsString().split(":")[0]);
                int verseNum = Integer.parseInt(verse.get("verse_key").getAsString().split(":")[1]);
                String text = verse.get("text_imlaei").getAsString();
                Log.d(Constants.ZIKR_TAG,TAG + " deserialize: check surahs" + "id " + id +
                        "surahnum " + surahNum + "versenum " + verseNum + "text " + text);
                        quranApiList.add(new QuranApiData(id, surahNum, verseNum, text));
            }
            Log.d(Constants.ZIKR_TAG,TAG + " deserialize: quranapilist size" + quranApiList.size());
        }
        return new SurahData(surahNum,quranApiList);
    }
}
