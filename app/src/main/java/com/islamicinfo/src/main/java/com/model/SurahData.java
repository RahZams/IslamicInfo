package com.islamicinfo.src.main.java.com.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.islamicinfo.src.main.java.com.utilities.SurahDataConverter;
import com.islamicinfo.src.main.java.com.utilities.SurahDataConverter;

import java.util.List;

@Entity
public class SurahData {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private int surahNum;
    @TypeConverters(SurahDataConverter.class)
    private List<QuranApiData> surahAyahsText;

    public SurahData(int surahNum, List<QuranApiData> surahAyahsText) {
        this.surahNum = surahNum;
        this.surahAyahsText = surahAyahsText;
    }

    public int getSurahNum() {
        return surahNum;
    }

    public void setSurahNum(int surahNum) {
        this.surahNum = surahNum;
    }

    public List<QuranApiData> getSurahAyahsText() {
        return surahAyahsText;
    }

    public void setSurahAyahsText(List<QuranApiData> surahAyahsText) {
        this.surahAyahsText = surahAyahsText;
    }

    @Override
    public String toString() {
        return "SurahData{" +
                "surahNum=" + surahNum +
                ", surahAyahsText=" + surahAyahsText.toString() +
                '}';
    }
}
