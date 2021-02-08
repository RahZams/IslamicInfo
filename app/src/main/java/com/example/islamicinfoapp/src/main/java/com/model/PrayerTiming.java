package com.example.islamicinfoapp.src.main.java.com.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PrayerTiming {
        @NonNull
        private String fajr;
        @NonNull
        private String sunsrise;
        @NonNull
        private String dhuhr;
        @NonNull
        private String asr;
        @NonNull
        private String sunset;
        @NonNull
        private String maghrib;
        @NonNull
        private String isha;
        @NonNull
        private String imsak;
        //    @NonNull
//    @PrimaryKey(autoGenerate = false)
        @NonNull
        private String prayerTimeEngDate;
        @NonNull
        private String hijridate;
        @NonNull
        private String hijriDay;
        @NonNull
        private int hijrimonthnumber;
        @NonNull
        private String hijrimonthname;
        @NonNull
        private String hijriyear;
        private String city;
        private String country;
        @NonNull
        @PrimaryKey(autoGenerate = true)
        private int rowid;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public PrayerTiming(String fajr, String sunsrise, String dhuhr, String asr, String sunset,
                            String maghrib, String isha, String imsak, String prayerTimeEngDate,
                            String hijridate, String hijriDay, int hijrimonthnumber, String hijrimonthname,
                            String hijriyear) {
            this.fajr = fajr;
            this.sunsrise = sunsrise;
            this.dhuhr = dhuhr;
            this.asr = asr;
            this.sunset = sunset;
            this.maghrib = maghrib;
            this.isha = isha;
            this.imsak = imsak;
            this.prayerTimeEngDate = prayerTimeEngDate;
            this.hijridate = hijridate;
            this.hijriDay = hijriDay;
            this.hijrimonthnumber = hijrimonthnumber;
            this.hijrimonthname = hijrimonthname;
            this.hijriyear = hijriyear;
        }

        public String getFajr() {
            return fajr;
        }

        public void setFajr(String fajr) {
            this.fajr = fajr;
        }

        public String getSunsrise() {
            return sunsrise;
        }

        public void setSunsrise(String sunsrise) {
            this.sunsrise = sunsrise;
        }

        public String getDhuhr() {
            return dhuhr;
        }

        public int getRowid() {
            return rowid;
        }

        public void setRowid(int rowid) {
            this.rowid = rowid;
        }

        public void setDhuhr(String dhuhr) {
            this.dhuhr = dhuhr;
        }

        public String getAsr() {
            return asr;
        }

        public void setAsr(String asr) {
            this.asr = asr;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getMaghrib() {
            return maghrib;
        }

        public void setMaghrib(String maghrib) {
            this.maghrib = maghrib;
        }

        public String getIsha() {
            return isha;
        }

        public void setIsha(String isha) {
            this.isha = isha;
        }

        public String getImsak() {
            return imsak;
        }

        public void setImsak(String imsak) {
            this.imsak = imsak;
        }

        public String getPrayerTimeEngDate() {
            return prayerTimeEngDate;
        }

        public void setPrayerTimeEngDate(String prayerTimeEngDate) {
            this.prayerTimeEngDate = prayerTimeEngDate;
        }

        public String getHijridate() {
            return hijridate;
        }

        public void setHijridate(String hijridate) {
            this.hijridate = hijridate;
        }

        public String getHijriDay() {
            return hijriDay;
        }

        public void setHijriDay(String hijriDay) {
            this.hijriDay = hijriDay;
        }

        public int getHijrimonthnumber() {
            return hijrimonthnumber;
        }

        public void setHijrimonthnumber(int hijrimonthnumber) {
            this.hijrimonthnumber = hijrimonthnumber;
        }

        public String getHijrimonthname() {
            return hijrimonthname;
        }

        public void setHijrimonthname(String hijrimonthname) {
            this.hijrimonthname = hijrimonthname;
        }

        public String getHijriyear() {
            return hijriyear;
        }

        public void setHijriyear(String hijriyear) {
            this.hijriyear = hijriyear;
        }
    }

