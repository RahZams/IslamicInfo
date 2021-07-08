package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.databinding.FragmentHomeBinding;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTiming;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTimingItem;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.PrayerTimeViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //
//    @BindView(R.id.recyclerview)
//    RecyclerView mRecyclerView;
//
//    @BindView(R.id.city_name)
//    TextView mCityCountryName;
//
//    @BindView(R.id.date_text)
//    TextView mDateView;
    Toolbar toolbar;

    private PrayerTimeViewModel mPrayerTimeViewModel;
    private ArrayList<PrayerTimingItem> mPrayerTimeList = new ArrayList<>();
    private PrayerTimeAdapter adapter;
    private String mCityname, mCountryname;
    private AppBarConfiguration mAppBarConfig;
    private NavController mNavController;
    private static final String TAG = HomeFragment.class.getSimpleName();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("plain/text");
//        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "some@email.address" });
//        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
//        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
//        startActivity(Intent.createChooser(intent, ""));
        // Inflate the layout for this fragment
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,
                container, false);
        View view = binding.getRoot();

//        View view  = inflater.inflate(R.layout.fragment_home,container,false);
//        ButterKnife.bind(this,view);
        mCityname = getActivity().getIntent().getStringExtra(getActivity().getString(R.string.cityname));
        mCountryname = getActivity().getIntent().getStringExtra(getActivity().getString(R.string.countryname));
        mPrayerTimeViewModel = ViewModelProviders.of(this).get(PrayerTimeViewModel.class);
        //binding.cityName.setText(mCityname + "," +
        binding.cityName.setText(mCityname);
        //binding.dateText.setText(Utility.getCurrentDate());
//   m     mCityCountryName.setText(mCityname + "," + mCountryname);
//        mDateView.setText(Utility.getCurrentDate());
        adapter = new PrayerTimeAdapter(getContext(), mPrayerTimeList);
        observeViewModel(mCityname, mCountryname, binding);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerview.setAdapter(adapter);
        //Intent intent = new Intent(getContext(), ReminderService.class);
        //getContext().startService(intent);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Constants.PRAYER_TAG, TAG + " onViewCreated: ");
        toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mAppBarConfig = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.zikrFragment,
                R.id.pregInfoFragment, R.id.helpFragment).build();
        mNavController = NavHostFragment.findNavController(this);
        NavigationUI.setupWithNavController(toolbar, mNavController, mAppBarConfig);

    }

    //    private void observeViewModel() {
//        Log.d("prayer", "observeViewModel: " +
//                mPrayerTimeViewModel.mTimingMutableLiveData == null ? "null" :
//                "not null");
//        mPrayerTimeViewModel.mTimingMutableLiveData.observe(this, prayerTiming -> {
//            if (prayerTiming != null) {
//                Log.d("prayer", "onChanged: city" + prayerTiming.getCity() + prayerTiming.getCountry());
//                adapter.updateList(createArrayListOfPrayerTiming(prayerTiming));
//            }
//        });
//    }

    private void observeViewModel(String mCityname, String mCountryname, FragmentHomeBinding binding) {
        Log.d(Constants.PRAYER_TAG, TAG + " observeViewModel: " + Utility.getCurrentDate() + mCityname + mCountryname);
        QuranDatabase.getInstance(getActivity()).quranDao().getPrayerTimingOfCity(mCityname, mCountryname,
                Utility.getCurrentDate()).observe(this, new Observer<PrayerTiming>() {
            @Override
            public void onChanged(PrayerTiming prayerTiming) {
//                Log.d("prayer", "onChanged: city" + prayerTiming.getCity());
                //Log.d("prayer", "onChanged: "  + (prayerTiming == null || prayerTiming.toString().equals(""))? "null":prayerTiming.getCity());
                if (prayerTiming != null && !prayerTiming.equals("")) {
                    Log.d(Constants.PRAYER_TAG, TAG + " observeViewModel onChanged: " + mCityname + prayerTiming.getCity());
                    binding.dateText.setText(prayerTiming.getPrayerTimeEngDate());
                    adapter.updateList(mCityname, mCountryname, createArrayListOfPrayerTiming(prayerTiming),
                            checkIfNewLocationToAssignReminders(mCityname,mCountryname));
                }
            }
        });
    }

    private boolean checkIfNewLocationToAssignReminders(String mCityname, String mCountryname) {
        boolean returnValue = false;
        if (!SharedPrefsHelper.getValue(getActivity(), getActivity().getResources().
                getString(R.string.new_location)).isEmpty()) {
            Log.d(Constants.PRAYER_TAG, TAG + " checkIfNewLocationToAssignReminders: not empty" +
                    SharedPrefsHelper.getValue(getActivity(), getActivity().getResources().
                            getString(R.string.new_location)).split(",")[0] +
                    SharedPrefsHelper.getValue(getActivity(), getActivity().getResources().
                            getString(R.string.new_location)).split(",")[1]);
            if ((SharedPrefsHelper.getValue(getActivity(), getActivity().getResources().
                    getString(R.string.new_location)).split(",")[0].equals(mCityname)) &&
                    (SharedPrefsHelper.getValue(getActivity(), getActivity().getResources().
                            getString(R.string.new_location)).split(",")[1].equals(mCountryname))) {
                SharedPrefsHelper.storeValue(getActivity(), getActivity().getResources().
                        getString(R.string.new_location), "");
                returnValue = true;
            }
        }
        return returnValue;
    }


    private ArrayList<PrayerTimingItem> createArrayListOfPrayerTiming(PrayerTiming prayerTiming) {

        int imgId = 0;
        String name = "";
        String time = "";
        boolean reminderSet = false;

        Log.d(Constants.PRAYER_TAG, TAG + " createArrayListOfPrayerTiming:before assigning "
                + mPrayerTimeList.size());
        mPrayerTimeList.clear();

        for (int i=0;i<6;i++){
            switch (i) {
                case 0:
                    imgId = R.drawable.fajr;
                    name = getResources().getString(R.string.fajr);
                    time = Utility.changeTimeFormat(prayerTiming.getFajr());
                    reminderSet = false;
                    break;

                case 1:
                    imgId = R.drawable.sunrise;
                    name = getResources().getString(R.string.sunrise);
                    time = Utility.changeTimeFormat(prayerTiming.getSunsrise());
                    reminderSet = false;
                    break;

                case 2:
                    imgId = R.drawable.zohr;
                    name = getResources().getString(R.string.dhuhr);
                    time = Utility.changeTimeFormat(prayerTiming.getDhuhr());
                    reminderSet = false;
                    break;

                case 3:
                    imgId = R.drawable.asr;
                    name = getResources().getString(R.string.asr);
                    time = Utility.changeTimeFormat(prayerTiming.getAsr());
                    reminderSet = false;
                    break;

//                case 4:
//                    imgId = R.drawable.sunset;
//                    name = getResources().getString(R.string.sunset);
//                    time = prayerTiming.getSunset();
//                    reminderSet = false;
//                    break;

                case 4:
                    imgId = R.drawable.maghrib;
                    name = getResources().getString(R.string.maghrib);
                    time = Utility.changeTimeFormat(prayerTiming.getMaghrib());
                    reminderSet = false;
                    break;

                case 5:
                    imgId = R.drawable.isha;
                    name = getResources().getString(R.string.isha);
                    time = Utility.changeTimeFormat(prayerTiming.getIsha());
                    reminderSet = false;
                    break;

                default:
                    break;
            }
            mPrayerTimeList.add(new PrayerTimingItem(name,time,imgId,reminderSet));
            }
        Log.d(Constants.PRAYER_TAG, TAG + " createArrayListOfPrayerTiming: " + mPrayerTimeList.size());
            return mPrayerTimeList;
        }

//    private String changeDateFormat(String timeData) {
//        Date dateTime;
//        String finalTime = "";
//        try {
//            if (!timeData.startsWith("12")) {
//                Log.d("prayer", "changeDateFormat: if");
//                dateTime = new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(timeData);
//                finalTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateTime);
//            }
//            else if (timeData.startsWith("12")){
//                Log.d("prayer", "changeDateFormat: else");
//                finalTime = timeData + " " + "PM";
//            }
//            Log.d("prayer", "changeDateFormat: " + finalTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
////            DateTimeFormatter parser = DateTimeFormatter.ofPattern("hh:mm",Locale.ENGLISH);
////            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm aa",Locale.ENGLISH);
////            LocalTime time = LocalTime.parse(timeData,parser);
////            finalTime = time.format(formatter);
////        }
//        return finalTime;
//    }

}

