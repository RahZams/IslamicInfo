package com.example.islamicinfoapp.src.main.java.com.model;

public class DuaApiData {
    private int dataNumber;
    private String text;
    private String language;
    private int surahNumber;
    private String surahName;
    private int juz;

    public DuaApiData(int dataNumber, String text, String language, int surahNumber,
                      String surahName, int juz) {
        this.dataNumber = dataNumber;
        this.text = text;
        this.language = language;
        this.surahNumber = surahNumber;
        this.surahName = surahName;
        this.juz = juz;
    }

    public int getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(int dataNumber) {
        this.dataNumber = dataNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getSurahNumber() {
        return surahNumber;
    }

    public void setSurahNumber(int surahNumber) {
        this.surahNumber = surahNumber;
    }

    public String getSurahName() {
        return surahName;
    }

    public void setSurahName(String surahName) {
        this.surahName = surahName;
    }

    public int getJuz() {
        return juz;
    }

    public void setJuz(int juz) {
        this.juz = juz;
    }
}
