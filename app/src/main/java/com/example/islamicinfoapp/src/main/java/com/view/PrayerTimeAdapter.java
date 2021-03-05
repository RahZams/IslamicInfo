package com.example.islamicinfoapp.src.main.java.com.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.databinding.PrayertimeItemBinding;
import com.example.islamicinfoapp.src.main.java.com.Receivers.ReminderReceiver;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTimingItem;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;

import java.util.ArrayList;
import java.util.Calendar;

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
                Log.d("prayer", "onClick: " + holder.binding.reminderImage.getDrawable());
                Intent intent = new Intent(mContext, ReminderReceiver.class);
                PendingIntent pendingIntent = null;
                switch(holder.binding.namazName.getText().toString()){
                    case Constants.FAJR:
                        intent.putExtra(mContext.getResources().getString(R.string.fajr),
                                holder.binding.namazTiming.getText().toString());
                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.FAJR_ID,intent,0);
                        break;
                    case Constants.SUNRISE:
                        intent.putExtra(mContext.getResources().getString(R.string.sunrise),
                                holder.binding.namazTiming.getText().toString());
                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.SUNRISE_ID,intent,0);
                        break;
                    case Constants.DHUHR:
                        intent.putExtra(mContext.getResources().getString(R.string.dhuhr),
                                holder.binding.namazTiming.getText().toString());
                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.DHUHR_ID,intent,0);
                        break;
                    case Constants.ASR:
                        intent.putExtra(mContext.getResources().getString(R.string.asr),
                                holder.binding.namazTiming.getText().toString());
                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ASR_ID,intent,0);
                        break;
                    case Constants.MAGHRIB:
                        intent.putExtra(mContext.getResources().getString(R.string.maghrib),
                                holder.binding.namazTiming.getText().toString());
                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.MAGHRIB_ID,intent,0);
                        break;
                    case Constants.ISHA:
                        intent.putExtra(mContext.getResources().getString(R.string.isha),
                                holder.binding.namazTiming.getText().toString());
                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ISHA_ID,intent,0);
                        break;
                    default:
                        break;
                }
                if (holder.binding.reminderImage.getDrawable().getConstantState().
                        equals(mContext.getResources().getDrawable(R.drawable.ic_notifications_off).getConstantState())){
                    Log.d("prayer", "onClick:if ");
                        holder.binding.reminderImage.setImageDrawable
                                (mContext.getResources().getDrawable(R.drawable.ic_notifications_on));
                    setupReminder(holder.binding.namazName.getText().toString(),
                            holder.binding.namazTiming.getText().toString(),pendingIntent);
                }
                else if (holder.binding.reminderImage.getDrawable().getConstantState() ==
                mContext.getResources().getDrawable(R.drawable.ic_notifications_on).getConstantState()){
                    Log.d("prayer", "onClick: else if");
                    holder.binding.reminderImage.setImageDrawable
                            (mContext.getResources().getDrawable(R.drawable.ic_notifications_off));
                    cancelReminder(pendingIntent);
                }
                else{
                    Log.d("prayer", "onClick:else ");
                }
            }
        });

    }

    private void cancelReminder(PendingIntent pendingIntent) {
        Log.d("prayer", "cancelReminder: ");
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void setupReminder(String namazName, String namazTiming, PendingIntent pendingIntent) {
        Log.d("prayer", "setupReminder: " + "time" + namazTiming);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        namazTiming = Utility.changeDateFormat(namazTiming);
        Log.d("prayer", "setupReminder: " + namazTiming);
        String[] timing = namazTiming.split(":");
        Log.d("prayer", "setupReminder: length" + timing.length + timing[0] + timing[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timing[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(timing[1]));
        cal.set(Calendar.SECOND,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),24*60*60*1000,pendingIntent);
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






