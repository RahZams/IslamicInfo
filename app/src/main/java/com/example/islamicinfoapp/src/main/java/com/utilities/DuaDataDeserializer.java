package com.example.islamicinfoapp.src.main.java.com.utilities;

import com.example.islamicinfoapp.src.main.java.com.model.DuaApiData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DuaDataDeserializer implements JsonDeserializer<DuaApiData> {
    @Override
    public DuaApiData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject data = json.getAsJsonObject().get("data").getAsJsonObject();
        int dataNumber = data.get("number").getAsInt();
        String text = data.get("text").getAsString();
        String language = data.get("edition").getAsJsonObject().get("language").getAsString();
        int surahNumber = data.get("surah").getAsJsonObject().get("number").getAsInt();
        String surahName = data.get("surah").getAsJsonObject().get("name").getAsString();
        int juz = data.get("juz").getAsInt();
        DuaApiData duaApiData =  new DuaApiData(dataNumber,text,language,surahNumber,surahName,juz);
        return duaApiData;
    }
}
