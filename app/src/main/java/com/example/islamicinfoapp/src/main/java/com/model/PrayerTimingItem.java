package com.example.islamicinfoapp.src.main.java.com.model;

import android.content.Context;

public class PrayerTimingItem {
    public static Context mContext;
    private String mNamazName;
    private String mNamazTime;
    private int mNamazImage;
    private boolean mIsReminderSet;

    public PrayerTimingItem(String mNamazName, String mNamazTime, int mNamazImage, boolean mIsReminderSet) {
        this.mNamazName = mNamazName;
        this.mNamazTime = mNamazTime;
        this.mNamazImage = mNamazImage;
        this.mIsReminderSet = mIsReminderSet;
    }

    public String getmNamazName() {
        return mNamazName;
    }

    public void setmNamazName(String mNamazName) {
        this.mNamazName = mNamazName;
    }

    public String getmNamazTime() {
        return mNamazTime;
    }

    public void setmNamazTime(String mNamazTime) {
        this.mNamazTime = mNamazTime;
    }

    public int getmNamazImage() {
        return mNamazImage;
    }

    public void setmNamazImage(int mNamazImage) {
        this.mNamazImage = mNamazImage;
    }

    public boolean ismIsReminderSet() {
        return mIsReminderSet;
    }

    public void setmIsReminderSet(boolean mIsReminderSet) {
        this.mIsReminderSet = mIsReminderSet;
    }
}
