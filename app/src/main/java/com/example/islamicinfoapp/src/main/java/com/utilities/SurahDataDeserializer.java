package com.example.islamicinfoapp.src.main.java.com.utilities;

import android.util.Log;

import com.example.islamicinfoapp.src.main.java.com.model.SurahData;
import com.example.islamicinfoapp.src.main.java.com.model.SurahAyahItemApiData;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SurahDataDeserializer implements JsonDeserializer {

    private List<SurahAyahItemApiData> surahAyahItemApiDataArrayList;
    private JsonObject surahData, ayahData;
    private int surahNum, numOfAyahs, numInSurah;
    private String surahName, engName, aayahTxt;
    private JsonArray jsonArrayOfAyahs;
    private boolean sajda;


    @Override
    public SurahData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        //Log.d("surah", "deserialize: " + json.toString());
        surahData = json.getAsJsonObject().get("data").getAsJsonObject();
        surahNum = surahData.get("number").getAsInt();
        surahName = surahData.get("name").getAsString();
        engName = surahData.get("englishName").getAsString();
        numOfAyahs = surahData.get("numberOfAyahs").getAsInt();
        jsonArrayOfAyahs = surahData.get("ayahs").getAsJsonArray();
        if (jsonArrayOfAyahs != null) {
            surahAyahItemApiDataArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArrayOfAyahs.size(); i++) {
                ayahData = jsonArrayOfAyahs.get(i).getAsJsonObject();
                //int aayahNum = obj.get("number").getAsInt();
                aayahTxt = ayahData.get("text").getAsString();
                //int juz = obj.get("juz").getAsInt();
                numInSurah = ayahData.get("numberInSurah").getAsInt();
                //Log.d("surah", "deserialize: " + numInSurah + " " + aayahTxt);
                if (ayahData.get("sajda").isJsonObject())
                    sajda = ayahData.get("sajda").getAsJsonObject().get("recommended").getAsBoolean() == true ? true
                            : ayahData.get("sajda").getAsJsonObject().get("obligatory").getAsBoolean();
                else
                    sajda = ayahData.get("sajda").getAsBoolean();

                //Log.d("surah", "deserialize: " + sajda);
                surahAyahItemApiDataArrayList.add(new SurahAyahItemApiData(aayahTxt, numInSurah, sajda));
            }
        }
        return new SurahData(surahNum, surahName, engName, numOfAyahs, surahAyahItemApiDataArrayList);

        /*if (data != null){
            int dataNumber = data.get("number").getAsInt();
            String surahNameUrdu = data.get("name").getAsString();
            String  surahNameEnglish = data.get("englishName").getAsString();
            int numberOfAyahs = data.get("numberOfAyahs").getAsInt();
            JsonArray ayahs = data.get("ayahs").getAsJsonArray();
            for (int i=0;i<ayahs.size();i++){
                JsonObject ayah = ayahs.get(i).getAsJsonObject();
                String surahText = ayah.get("text").getAsString();
                int numberInSurah = ayah.get("numberInSurah").getAsInt();
                int juz = ayah.get("juz").getAsInt();
                boolean sajda = ayah.get("sajda").getAsBoolean();
                SurahAyahItemApiData surahAyahItemApiData = new SurahAyahItemApiData
                        (surahText,numberInSurah,juz,sajda);
                surahAyahItemApiDataArrayList.add(surahAyahItemApiData);
            }
            SurahData surahData = new SurahData(dataNumber,surahNameUrdu,surahNameEnglish,
                    numberOfAyahs,surahAyahItemApiDataArrayList);
            return surahData;

        }
        else
            return null;*/
    }
}
