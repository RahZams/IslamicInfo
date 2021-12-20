package com.islamicinfo.src.main.java.com.model;

public class QuranApiData {
    private int id;
    private int surahNum;
    private int verseNum;
    private String ayahText;

    public QuranApiData(int id, int surahNum, int verseNum, String ayahText) {
        this.id = id;
        this.surahNum = surahNum;
        this.verseNum = verseNum;
        this.ayahText = ayahText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurahNum() {
        return surahNum;
    }

    public void setSurahNum(int surahNum) {
        this.surahNum = surahNum;
    }

    public int getVerseNum() {
        return verseNum;
    }

    public void setVerseNum(int verseNum) {
        this.verseNum = verseNum;
    }

    public String getAyahText() {
        return ayahText;
    }

    public void setAyahText(String ayahText) {
        this.ayahText = ayahText;
    }

    @Override
    public String toString() {
        return "QuranApiData{" +
                "id=" + id +
                ", surahNum=" + surahNum +
                ", verseNum=" + verseNum +
                ", ayahText='" + ayahText + '\'' +
                '}';
    }
}
