package com.example.islamicinfoapp.src.main.java.com.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QuranDbData {
    public String quranText;
    public String name;
    @PrimaryKey(autoGenerate = true)
    public int rowid;

    public QuranDbData(String quranText, String name) {
        this.quranText = quranText;
        this.name = name;
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
