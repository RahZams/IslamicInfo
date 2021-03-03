package com.example.islamicinfoapp.src.main.java.com.view;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.databinding.PrayertimeItemBinding;
import com.example.islamicinfoapp.src.main.java.com.Receivers.ReminderReceiver;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTimingItem;
import java.util.ArrayList;

public class PrayerTimeAdapter extends RecyclerView.Adapter<PrayerTimeAdapter.PrayerItemViewHolder> {

    private ArrayList<PrayerTimingItem> mPrayerTimeList;
    private Context mContext;

    public PrayerTimeAdapter(Context context, ArrayList<PrayerTimingItem> mPrayerTimeList) {
        Log.d("prayer", "PrayerTimeAdapter: " + mPrayerTimeList.size());
        this.mContext = context;
        this.mPrayerTimeList = mPrayerTimeList;

    }

    public void updateList(ArrayList<PrayerTimingItem> arrayListOfPrayerTiming) {
        this.mPrayerTimeList = arrayListOfPrayerTiming;
        Log.d("prayer", "updateList: " + mPrayerTimeList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PrayerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("prayer", "onCreateViewHolder: ");
                PrayertimeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.prayertime_item,
                parent,false);
        return new PrayerItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PrayerItemViewHolder holder, int position) {
        holder.binding.namazName.setText(mPrayerTimeList.get(position).getmNamazName());
        holder.binding.namazImage.setImageResource(mPrayerTimeList.get(position).getmNamazImage());
//        holder.mNamazName.setCompoundDrawablesWithIntrinsicBounds(mPrayerTimeList.get(position).getmNamazImage()
//                , 0, 0, 0);
        holder.binding.namazTiming.setText(mPrayerTimeList.get(position).getmNamazTime());
        holder.binding.reminderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.binding.reminderImage.getDrawable() ==
                        mContext.getResources().getDrawable(R.drawable.ic_notifications_off)){
                        holder.binding.reminderImage.setImageDrawable
                                (mContext.getResources().getDrawable(R.drawable.ic_notifications_on));
                        Intent intent = new Intent(mContext, ReminderReceiver.class);
                    PendingIntent pendingIntent = null;
                    switch(holder.binding.namazName.getText().toString()){
                        case mContext.getResources().getString(R.string.fajr):

                            break;
                        case mContext.getResources().getString(R.string.sunrise):
                            break;
                        case mContext.getResources().getString(R.string.dhuhr):
                            break;
                        case mContext.getResources().getString(R.string.asr):
                            break;
                        case mContext.getResources().getString(R.string.maghrib):
                            break;
                        case mContext.getResources().getString(R.string.isha):
                            break;
                        default:
                            break;
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPrayerTimeList.size();
    }

    public class PrayerItemViewHolder extends RecyclerView.ViewHolder {
        PrayertimeItemBinding binding;
//
//        TextView mNamazName,mNamazTime;
//        ImageView mReminder;


        public PrayerItemViewHolder(@NonNull PrayertimeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
//            mNamazName = binding.namazName;
//            mNamazTime = binding.namazTiming;
//            mReminder = binding.reminderImage;
        }
    }

}


//
//    @NonNull
//    @Override
//    public PrayerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("prayer", "onCreateViewHolder: ");
//        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.prayertime_item,parent,false);
//        return new PrayerItemViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PrayerItemViewHolder holder, int position) {
//        holder.mNamazName.setText(mPrayerTimeList.get(position).getmNamazName());
//        holder.mNamazImage.setImageResource(mPrayerTimeList.get(position).getmNamazImage());
////        holder.mNamazName.setCompoundDrawablesWithIntrinsicBounds(mPrayerTimeList.get(position).getmNamazImage()
////                , 0, 0, 0);
//        holder.mNamazTime.setText(mPrayerTimeList.get(position).getmNamazTime());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mPrayerTimeList.size();
//    }
//
//    public class PrayerItemViewHolder extends RecyclerView.ViewHolder {
//
//        TextView mNamazName, mNamazTime;
//        ImageView mNamazImage,mReminder;
//
//
//        public PrayerItemViewHolder(@NonNull View view) {
//            super(view);
//            mNamazName = view.findViewById(R.id.namazName);
//            mNamazTime = view.findViewById(R.id.namazTiming);
//            mNamazImage = view.findViewById(R.id.namazImage);
//            mReminder = view.findViewById(R.id.reminderImage);
//        }
//    }
//}






