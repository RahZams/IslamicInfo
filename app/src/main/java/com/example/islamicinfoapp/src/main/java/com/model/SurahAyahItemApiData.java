package com.example.islamicinfoapp.src.main.java.com.model;

public class SurahAyahItemApiData {
    private String ayahText;
    private int numberInSurah;
    //private int juz;
    private boolean sajda;
    //private int surahNum;


    public SurahAyahItemApiData(String ayahText, int numberInSurah, boolean sajda) {
        this.ayahText = ayahText;
        this.numberInSurah = numberInSurah;
        this.sajda = sajda;
    }

    public String getAyahText() {
        return ayahText;
    }

    public void setAyahText(String ayahText) {
        this.ayahText = ayahText;
    }

    public int getNumberInSurah() {
        return numberInSurah;
    }

    public void setNumberInSurah(int numberInSurah) {
        this.numberInSurah = numberInSurah;
    }

    public boolean isSajda() {
        return sajda;
    }

    public void setSajda(boolean sajda) {
        this.sajda = sajda;
    }
}

