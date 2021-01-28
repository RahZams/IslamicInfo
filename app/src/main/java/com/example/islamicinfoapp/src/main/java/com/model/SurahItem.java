package com.example.islamicinfoapp.src.main.java.com.model;

public class SurahItem {
    private String mSurahItemTitle,mSurahItemText;

    public SurahItem(String mSurahItemTitle, String mSurahItemText) {
        this.mSurahItemTitle = mSurahItemTitle;
        this.mSurahItemText = mSurahItemText;
    }

    public String getmSurahItemTitle() {
        return mSurahItemTitle;
    }

    public void setmSurahItemTitle(String mSurahItemTitle) {
        this.mSurahItemTitle = mSurahItemTitle;
    }

    public String getmSurahItemText() {
        return mSurahItemText;
    }

    public void setmSurahItemText(String mSurahItemText) {
        this.mSurahItemText = mSurahItemText;
    }
}
