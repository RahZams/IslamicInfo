package com.example.islamicinfoapp.src.main.java.com.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.islamicinfoapp.src.main.java.com.utilities.SurahDataConverter;

import java.util.List;

@Entity
public class SurahData {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private int dataNumber;
    @NonNull
    private String surahNameUrdu;
    @NonNull
    private String surahNameEnglish;
    @NonNull
    private int numberOfAyahs;
    @TypeConverters(SurahDataConverter.class)
    private List<SurahAyahItemApiData> ayahList;

    public SurahData(int dataNumber, String surahNameUrdu, String surahNameEnglish,
                     int numberOfAyahs, List<SurahAyahItemApiData> ayahList) {
        this.dataNumber = dataNumber;
        this.surahNameUrdu = surahNameUrdu;
        this.surahNameEnglish = surahNameEnglish;
        this.numberOfAyahs = numberOfAyahs;
        this.ayahList = ayahList;
    }

    public int getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(int dataNumber) {
        this.dataNumber = dataNumber;
    }

    public String getSurahNameUrdu() {
        return surahNameUrdu;
    }

    public void setSurahNameUrdu(String surahNameUrdu) {
        this.surahNameUrdu = surahNameUrdu;
    }

    public String getSurahNameEnglish() {
        return surahNameEnglish;
    }

    public void setSurahNameEnglish(String surahNameEnglish) {
        this.surahNameEnglish = surahNameEnglish;
    }

    public List<SurahAyahItemApiData> getAyahList() {
        return ayahList;
    }

    public void setAyahList(List<SurahAyahItemApiData> ayahList) {
        this.ayahList = ayahList;
    }

    public int getNumberOfAyahs() {
        return numberOfAyahs;
    }

    public void setNumberOfAyahs(int numberOfAyahs) {
        this.numberOfAyahs = numberOfAyahs;
    }
    //    @PrimaryKey(autoGenerate = false)
//    private int numberInSurah;
//    private int juz;
//    private boolean sajda;


}
