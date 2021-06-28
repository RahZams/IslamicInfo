package com.example.islamicinfoapp.src.main.java.com.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.databinding.PrayertimeItemBinding;
import com.example.islamicinfoapp.src.main.java.com.Receivers.ReminderReceiver;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTimingItem;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PrayerTimeAdapter extends RecyclerView.Adapter<PrayerTimeAdapter.PrayerItemViewHolder> {

    private ArrayList<PrayerTimingItem> mPrayerTimeList;
    private Context mContext;
    private String mCityName,mCountryName;
    private static final String TAG = PrayerTimeAdapter.class.getSimpleName();

    public PrayerTimeAdapter(Context context, ArrayList<PrayerTimingItem> mPrayerTimeList) {
        Log.d(Constants.PRAYER_TAG, TAG + " PrayerTimeAdapter: " + mPrayerTimeList.size());
        this.mContext = context;
        this.mPrayerTimeList = mPrayerTimeList;
    }

    public void updateList(String cityname, String countryname,ArrayList<PrayerTimingItem> arrayListOfPrayerTiming) {
        mCityName = cityname;
        mCountryName = countryname;
        this.mPrayerTimeList = arrayListOfPrayerTiming;
        Log.d(Constants.PRAYER_TAG, TAG + " updateList: " + mPrayerTimeList.size() + mCityName);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PrayerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(Constants.PRAYER_TAG, TAG + " onCreateViewHolder: ");
                PrayertimeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.
                                from(parent.getContext()),R.layout.prayertime_item,parent,false);
        return new PrayerItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PrayerItemViewHolder holder, int position) {
        holder.binding.namazName.setText(mPrayerTimeList.get(position).getmNamazName());
        holder.binding.namazImage.setImageResource(mPrayerTimeList.get(position).getmNamazImage());
//        holder.mNamazName.setCompoundDrawablesWithIntrinsicBounds(mPrayerTimeList.get(position).getmNamazImage()
//                , 0, 0, 0);
        holder.binding.namazTiming.setText(mPrayerTimeList.get(position).getmNamazTime());
        Log.d(Constants.PRAYER_TAG,TAG +  " onBindViewHolder: checkIfNewLocationToAssignReminders"
                + checkIfNewLocationToAssignReminders());
        if(!checkIfNewLocationToAssignReminders()) {
            Log.d(Constants.PRAYER_TAG,TAG +  " onBindViewHolder: checkIfNewLocationToAssignReminders" );
            assignReminderImage(holder.binding.namazName.getText().toString(), holder.binding.reminderImage);
        }

        holder.binding.reminderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReminders(holder.binding.namazName.getText().toString(),
                        holder.binding.namazTiming.getText().toString(),holder.binding.reminderImage);
                Log.d(Constants.PRAYER_TAG, TAG + " onClick: " + holder.binding.reminderImage.getDrawable() +
                        mCityName);

                // removed from here and placed it in setReminders method

//                Intent intent = new Intent(mContext, ReminderReceiver.class);
//                PendingIntent pendingIntent = null;
//                switch(holder.binding.namazName.getText().toString()){
//                    case Constants.FAJR:
//                        intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                        intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                                holder.binding.namazName.getText().toString());
//                        intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                                holder.binding.namazTiming.getText().toString());
//                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.FAJR_ID,intent,0);
//                        break;
//                    case Constants.SUNRISE:
//                        intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                        intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                                holder.binding.namazName.getText().toString());
//                        intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                                holder.binding.namazTiming.getText().toString());
//                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.SUNRISE_ID,intent,0);
//                        break;
//                    case Constants.DHUHR:
//                        intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                        intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                                holder.binding.namazName.getText().toString());
//                        intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                                holder.binding.namazTiming.getText().toString());
//                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.DHUHR_ID,intent,0);
//                        break;
//                    case Constants.ASR:
//                        intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                        intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                                holder.binding.namazName.getText().toString());
//                        intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                                holder.binding.namazTiming.getText().toString());
//                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ASR_ID,intent,0);
//                        break;
//                    case Constants.MAGHRIB:
//                        intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                        intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                                holder.binding.namazName.getText().toString());
//                        intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                                holder.binding.namazTiming.getText().toString());
//                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.MAGHRIB_ID,intent,0);
//                        break;
//                    case Constants.ISHA:
//                        intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                        intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                                holder.binding.namazName.getText().toString());
//                        intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                                holder.binding.namazTiming.getText().toString());
//                        pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ISHA_ID,intent,0);
//                        break;
//                    default:
//                        break;
//                }
//                if (holder.binding.reminderImage.getDrawable().getConstantState().
//                        equals(mContext.getResources().getDrawable(R.drawable.ic_notifications_off).getConstantState())){
//                    Log.d("prayer", "onClick:if ");
//                        holder.binding.reminderImage.setImageDrawable
//                                (mContext.getResources().getDrawable(R.drawable.ic_notifications_on));
////                    setupReminder(holder.binding.namazName.getText().toString(),
////                            holder.binding.namazTiming.getText().toString(),pendingIntent);
//                    SharedPrefsHelper.storeValue(mContext,holder.binding.namazName.getText().toString(),true);
//                    setupReminder(holder.binding.namazName.getText().toString(),
//                            "04:05 PM",pendingIntent);
//                }
//                else if (holder.binding.reminderImage.getDrawable().getConstantState() ==
//                mContext.getResources().getDrawable(R.drawable.ic_notifications_on).getConstantState()){
//                    Log.d("prayer", "onClick: else if");
//                    SharedPrefsHelper.storeValue(mContext,holder.binding.namazName.getText().toString(),false);
//                    holder.binding.reminderImage.setImageDrawable
//                            (mContext.getResources().getDrawable(R.drawable.ic_notifications_off));
//                    cancelReminder(pendingIntent);
//                }
//                else{
//                    Log.d("prayer", "onClick:else ");
//                }
            }
        });

    }

    private boolean checkIfNewLocationToAssignReminders() {
        boolean returnValue = false;
        if (!SharedPrefsHelper.getValue(mContext,mContext.getResources().getString(R.string.new_location)).isEmpty()){
            if ((SharedPrefsHelper.getValue(mContext,mContext.getResources().
                    getString(R.string.new_location)).split(",")[0].equals(mCityName)) &&
                    (SharedPrefsHelper.getValue(mContext,mContext.getResources().
                            getString(R.string.new_location)).split(",")[0].equals(mCountryName))){
                return true;
            }
        }
        return returnValue;
    }

    private void setReminders(String namazName, String namazTiming, ImageView reminderImage) {
        String sharedPrefsValue = "";
        Log.d(Constants.PRAYER_TAG, TAG + " setReminders: " + namazName + namazTiming);
//        PendingIntent pendingIntent = Utility.createPendingIntent(mContext,namazName,namazTiming,mCityName,mCountryName);

        // placed it in utility class method

//        Intent intent = new Intent(mContext, ReminderReceiver.class);
//        PendingIntent pendingIntent = null;
//        switch(namazName){
//            case Constants.FAJR:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
//                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.FAJR_ID,intent,0);
//                break;
//            case Constants.SUNRISE:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
//                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.SUNRISE_ID,intent,0);
//                break;
//            case Constants.DHUHR:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
//                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.DHUHR_ID,intent,0);
//                break;
//            case Constants.ASR:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
//                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ASR_ID,intent,0);
//                break;
//            case Constants.MAGHRIB:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
//                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.MAGHRIB_ID,intent,0);
//                break;
//            case Constants.ISHA:
//                intent.putExtra(mContext.getResources().getString(R.string.cityname),mCityName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazName),
//                        namazName);
//                intent.putExtra(mContext.getResources().getString(R.string.namazTime),
//                        namazTiming);
//                pendingIntent = PendingIntent.getBroadcast(mContext,Constants.ISHA_ID,intent,0);
//                break;
//            default:
//                break;
//        }
        if (reminderImage.getDrawable().getConstantState().
                equals(mContext.getResources().getDrawable(R.drawable.ic_notifications_off).getConstantState())){
            Log.d(Constants.PRAYER_TAG, TAG + " onClick:if set" + Utility.getCurrentDate() + "namazname" + namazName);
            reminderImage.setImageDrawable
                    (mContext.getResources().getDrawable(R.drawable.ic_notifications_on));
//                     setupReminder(holder.binding.namazName.getText().toString(),
//                            holder.binding.namazTiming.getText().toString(),pendingIntent);
            sharedPrefsValue = Utility.getCurrentDate() + "," + namazTiming + ",true";
            SharedPrefsHelper.storeValue(mContext,namazName,sharedPrefsValue);
            Log.d(Constants.PRAYER_TAG, TAG + " setReminders: sharedPrefsValue" + sharedPrefsValue);
            Log.d(Constants.PRAYER_TAG, TAG + " setReminders: " + Utility.getDateForApi(Utility.convertStringToDate(
                    Utility.getCurrentDate())));
//            Utility.setupReminder(mContext,Utility.getDateForApi(Utility.convertStringToDate(Utility.getCurrentDate())),
//                    "12:36 AM",pendingIntent);
            Utility.setupReminder(mContext,Utility.getDateForApi(Utility.convertStringToDate(Utility.getCurrentDate())),
                    namazTiming,
                    Utility.createPendingIntent(mContext,namazName,namazTiming,mCityName,mCountryName));
        }
        else if (reminderImage.getDrawable().getConstantState() ==
                mContext.getResources().getDrawable(R.drawable.ic_notifications_on).getConstantState()){
            Log.d(Constants.PRAYER_TAG, TAG + " onClick: else if cancel" + "namazname" + namazName);
            sharedPrefsValue = Utility.getCurrentDate() + "," + namazTiming +",false";
            SharedPrefsHelper.storeValue(mContext,namazName,sharedPrefsValue);
            reminderImage.setImageDrawable
                    (mContext.getResources().getDrawable(R.drawable.ic_notifications_off));
//            Utility.cancelReminder(mContext,pendingIntent);
            Utility.cancelReminder(mContext,namazName);
        }
        else{
            Log.d(Constants.PRAYER_TAG, TAG + " onClick:else ");
        }
    }

    private void assignReminderImage(String namazName, ImageView reminderImage) {
        Log.d(Constants.PRAYER_TAG, TAG + " assignReminderImage: " + "namazName:" + namazName);
//                SharedPrefsHelper.getValue(mContext,namazName).split("-")[0] +
//                SharedPrefsHelper.getValue(mContext,namazName).split("-")[1]
//        + SharedPrefsHelper.getValue(mContext,namazName).split("-")[2]);

//        if (SharedPrefsHelper.getValue(mContext,namazName)){
//            reminderImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications_on));
//        }

//        if (!SharedPrefsHelper.getValue(mContext,namazName).equals("")){
//            Log.d("prayer", "assignReminderImage: not equals");
//            if(SharedPrefsHelper.getValue(mContext,namazName).split("-")[2].equals("true")) {
//                Log.d("prayer", "assignReminderImage: if");
//                reminderImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications_on));
//            }
//        }

        if (!SharedPrefsHelper.getValue(mContext,namazName).equals("") &&
                SharedPrefsHelper.getValue(mContext,namazName).split(",")[2].equals("true")){
//            Log.d("prayer", "assignReminderImage: not blank" + SharedPrefsHelper.getValue(mContext,namazName)
//             + SharedPrefsHelper.getValue(mContext,namazName).split(","));
//            String[] sharedPrefsValue = SharedPrefsHelper.getValue(mContext,namazName).split(",");
//            Log.d("prayer", "assignReminderImage: sharedPrefsValue[0]" + sharedPrefsValue[0] +
//                    "sharedPrefsValue[1]" + sharedPrefsValue[1] + "sharedPrefsValue[2]" + sharedPrefsValue[2]);
//                if (sharedPrefsValue[2].equals("true")) {
                Log.d(Constants.PRAYER_TAG, TAG + " assignReminderImage: if " +
                        SharedPrefsHelper.getValue(mContext,namazName).split(",")[0] +
                        SharedPrefsHelper.getValue(mContext,namazName).split(",")[1] +
                        SharedPrefsHelper.getValue(mContext,namazName).split(",")[2]);
                reminderImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications_on));
            //}
        }



    }

    // placed it in utility class methods

//    private void cancelReminder(PendingIntent pendingIntent) {
//        Log.d("prayer", "cancelReminder: ");
//        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(pendingIntent);
//    }

    // placed it in utility class methods

//    private void setupReminder(String namazName, String namazTiming, PendingIntent pendingIntent) {
//        Log.d("prayer", "setupReminder: " + "time" + namazTiming);
//        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
//        //namazTiming = Utility.changeDateFormat(namazTiming);
//        Log.d("prayer", "setupReminder: " + namazTiming);
//        String[] initTiming = namazTiming.split(" ");
//        String[] timing = initTiming[0].split(":");
//        Log.d("prayer", "setupReminder: length" + timing.length + timing[0] + timing[1]);
//
//        Calendar cal = Calendar.getInstance();
////        cal.setTimeInMillis(System.currentTimeMillis());
//        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timing[0]));
//        cal.set(Calendar.MINUTE, Integer.parseInt(timing[1]));
//        cal.set(Calendar.SECOND,0);
//        Log.d("prayer", "setupReminder: " + initTiming[1]);
//        if (initTiming[1].equals("AM")){
//            Log.d("prayer", "setupReminder: " + " if AM");
//            cal.set(Calendar.AM_PM,Calendar.AM);
//        }
//        else if (initTiming[1].equals("PM")){
//            Log.d("prayer", "setupReminder: " + "if PM");
//            cal.set(Calendar.AM_PM,Calendar.PM);
//        }
//
//        Log.d("prayer", "setupReminder: " + "hour:" + cal.get(Calendar.HOUR_OF_DAY) +
//                "minute:"+ cal.get(Calendar.MINUTE) + "am/pm:" + cal.get(Calendar.AM_PM));
//        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),24*60*60*1000,pendingIntent);
//        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
//        }
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
//        }
//        else{
//            alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
//        }
//    }


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






