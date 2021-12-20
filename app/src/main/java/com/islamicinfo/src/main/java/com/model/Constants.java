package com.islamicinfo.src.main.java.com.model;

import android.os.Build;

import androidx.annotation.IntegerRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class Constants {
    public static final int FAJR_ID = 100;
    public static final int SUNRISE_ID = 200;
    public static final int DHUHR_ID = 300;
    public static final int ASR_ID = 400;
    public static final int MAGHRIB_ID = 500;
    public static final int ISHA_ID = 600;
    public static final String FAJR = "Fajr";
    public static final String SUNRISE = "Sunrise";
    public static final String DHUHR = "Dhuhr";
    public static final String ASR = "Asr";
    public static final String MAGHRIB = "Maghrib";
    public static final String ISHA = "Isha";
    public static final String NOTIFICATION_CHANNEL = "Prayer Times Channel";
    public static final int NOTIFICATION_ID = 1;
    public static final String PRAYER_TAG = "prayer";
    public static final String SURAH_TAG = "surah";
    public static final String SPLASH = "splash";
    public static final String SURAHITEM = "surah_item";
    public static final String ZIKR_TAG = "zikr";
    public static final String LOC_TAG = "location_tag";
    public static final String PREG_TAG = "preg";
    public static final String HELP_TAG = "help";
    public static final Map<String, Integer> surah = new HashMap<String, Integer>() {{
        put("Surah Luqman", 31);
        put("Surah Yusuf", 12);
        put("Surah Maryam", 19);
        put("Surah Al-Baqara", 2);
        put("Surah Yunus", 10);
        put("Surah As-Saaffaat", 37);
        put("Surah Al-Mulk", 67);
        put("Surah Aal-i-Imraan", 3);
        put("Surah Al-Insaan", 76);
        //put("Surah Al-Qadr", 97);
        put("Surah Al-Kawthar", 108);
        put("Surah Al-Fath", 48);
        put("Surah An-Nasr", 110);
        put("Surah Al-Waaqia", 56);
        //put("Surah At-Tin", 95);
        put("Surah An-Nahl", 16);
        put("Surah Yaseen", 36);
        put("Surah Al-Ikhlaas", 112);
        put("Surah Al-Qadr", 97);
        put("Surah At-Tin", 95);
        put("Surah Al-Furqaan", 25);
        put("Surah Muhammad", 47);
        put("Surah Al-Asr", 103);
        put("Surah Adh-Dhaariyat", 51);
        put("Surah Al-Hajj", 22);
        put("Surah Faatir", 35);
        put("Surah An-Naba", 78);
        put("Surah An-Naas", 114);
        put("Surah Al-Falaq", 113);
        put("Surah Al-Kaafiroon", 109);
    }};
}
