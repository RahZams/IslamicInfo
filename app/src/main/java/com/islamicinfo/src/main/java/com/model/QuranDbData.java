package com.islamicinfo.src.main.java.com.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class QuranDbData {
    @PrimaryKey(autoGenerate = true)
    public int rowid;
    @NonNull
    public String name;
    @NonNull
    public String quranText;

    public QuranDbData(String name,String quranText) {
        this.name = name;
        this.quranText = quranText;
    }

    public String getQuranText() {
        return quranText;
    }

    public void setQuranText(String quranText) {
        this.quranText = quranText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }
}
