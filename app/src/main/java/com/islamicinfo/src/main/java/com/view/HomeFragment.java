package com.islamicinfo.src.main.java.com.view;


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
import android.widget.Toast;

import com.example.islamicinfo.R;
import com.example.islamicinfo.databinding.FragmentHomeBinding;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.model.PrayerTiming;
import com.islamicinfo.src.main.java.com.model.PrayerTimingItem;
import com.islamicinfo.src.main.java.com.model.QuranDatabase;
import com.islamicinfo.src.main.java.com.utilities.SharedPrefsHelper;
import com.islamicinfo.src.main.java.com.utilities.Utility;
import com.islamicinfo.src.main.java.com.viewmodel.PrayerTimeViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Toolbar toolbar;
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
        // Inflate the layout for this fragment

        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,
                container, false);
        View view = binding.getRoot();
        mCityname = getActivity().getIntent().getStringExtra(getActivity().getString(R.string.cityname));
        mCountryname = getActivity().getIntent().getStringExtra(getActivity().getString(R.string.countryname));
        mPrayerTimeViewModel = ViewModelProviders.of(this).get(PrayerTimeViewModel.class);
        binding.dateText.setText(Utility.getCurrentDate());
        binding.progressbar.setVisibility(View.VISIBLE);
        observeViewModel(mCityname, mCountryname, binding,mPrayerTimeList);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Constants.PRAYER_TAG, TAG + " onViewCreated: ");
        toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().
                setTitle(getActivity().getResources().getString(R.string.home_title));
        mAppBarConfig = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.zikrFragment,
                R.id.pregInfoFragment, R.id.helpFragment).build();
        mNavController = NavHostFragment.findNavController(this);
        NavigationUI.setupWithNavController(toolbar, mNavController, mAppBarConfig);

    }

    private void observeViewModel(String mCityname, String mCountryname, FragmentHomeBinding binding, ArrayList<PrayerTimingItem> mPrayerTimeList) {
        Log.d(Constants.PRAYER_TAG, TAG + " observeViewModel: " + Utility.getCurrentDate() + mCityname + mCountryname);
        QuranDatabase.getInstance(getActivity()).quranDao().getPrayerTimingOfCity(mCityname, mCountryname,
                Utility.getCurrentDate()).observe(this, new Observer<PrayerTiming>() {
            @Override
            public void onChanged(PrayerTiming prayerTiming) {
                if (prayerTiming != null && !prayerTiming.equals("")) {
                    Log.d(Constants.PRAYER_TAG, TAG + " observeViewModel onChanged: "
                            + mCityname + prayerTiming.getCity());
                    Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG);
                    binding.scrollview.setVisibility(View.VISIBLE);
                    binding.progressbar.setVisibility(View.GONE);
                    binding.noData.setVisibility(View.GONE);
                    binding.recyclerview.setVisibility(View.VISIBLE);
                    binding.cityName.setText(mCityname);
                    binding.dateText.setText(prayerTiming.getPrayerTimeEngDate());
                    adapter = new PrayerTimeAdapter(getContext(), mPrayerTimeList);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerview.setAdapter(adapter);
                    adapter.updateList(mCityname, mCountryname, createArrayListOfPrayerTiming(prayerTiming),
                            checkIfNewLocationToAssignReminders(mCityname,mCountryname));
                }
                else{
                    binding.progressbar.setVisibility(View.GONE);
                    binding.scrollview.setVisibility(View.VISIBLE);
                    binding.cityName.setText(mCityname);
                    binding.noData.setVisibility(View.VISIBLE);
                    binding.recyclerview.setVisibility(View.GONE);
                    binding.noData.setText(getActivity().getResources().getString(R.string.no_data_available));
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
}

