package com.example.islamicinfoapp.src.main.java.com.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTimingItem;

import java.util.ArrayList;

public class PrayerTimeAdapter extends RecyclerView.Adapter<PrayerTimeAdapter.PrayerItemViewHolder> {

    private ArrayList<PrayerTimingItem> mPrayerTimeList;

    public PrayerTimeAdapter(ArrayList<PrayerTimingItem> mPrayerTimeList) {
        this.mPrayerTimeList = mPrayerTimeList;

    }

    public void updateList(ArrayList<PrayerTimingItem> arrayListOfPrayerTiming) {
        this.mPrayerTimeList = arrayListOfPrayerTiming;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PrayerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.prayertime_item,parent,false);
        return new PrayerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrayerItemViewHolder holder, int position) {
        holder.mNamazName.setText(mPrayerTimeList.get(position).getmNamazName());
        holder.mNamazName.setCompoundDrawablesWithIntrinsicBounds(mPrayerTimeList.get(position).getmNamazImage()
        ,0,0,0);
        holder.mNamazTime.setText(mPrayerTimeList.get(position).getmNamazTime());

    }

    @Override
    public int getItemCount() {
        return mPrayerTimeList.size();
    }

    public class PrayerItemViewHolder extends RecyclerView.ViewHolder {
        TextView mNamazName,mNamazTime;
        ImageView mReminder;


        public PrayerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mNamazName = itemView.findViewById(R.id.namazName);
            mNamazTime = itemView.findViewById(R.id.namazTiming);
            mReminder = itemView.findViewById(R.id.reminderImage);
        }
    }
}
