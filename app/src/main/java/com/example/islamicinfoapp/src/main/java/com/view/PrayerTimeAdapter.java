package com.example.islamicinfoapp.src.main.java.com.view;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicinfoapp.src.main.java.com.model.PrayerTimingItem;

import java.util.ArrayList;

public class PrayerTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<PrayerTimingItem> mPrayerTimeList = new ArrayList<>();

    public PrayerTimeAdapter(ArrayList<PrayerTimingItem> mPrayerTimeList) {
        this.mPrayerTimeList = mPrayerTimeList;

    }

    public void updateList(ArrayList<PrayerTimingItem> arrayListOfPrayerTiming) {
        this.mPrayerTimeList = arrayListOfPrayerTiming;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
