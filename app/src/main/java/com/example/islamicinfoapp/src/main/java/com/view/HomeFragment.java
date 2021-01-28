package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.databinding.FragmentHomeBinding;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTiming;
import com.example.islamicinfoapp.src.main.java.com.model.PrayerTimingItem;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.PrayerTimeViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private PrayerTimeViewModel mPrayerTimeViewModel;
    private ArrayList<PrayerTimingItem> mPrayerTimeList = new ArrayList<>();
    private PrayerTimeAdapter adapter = new PrayerTimeAdapter(mPrayerTimeList);


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
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,
                container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this,view);
        mPrayerTimeViewModel = ViewModelProviders.of(this).get(PrayerTimeViewModel.class);
        observeViewModel();
        return view;
    }

    private void observeViewModel() {
        mPrayerTimeViewModel.mTimingMutableLiveData.observe(this, new Observer<PrayerTiming>() {
            @Override
            public void onChanged(PrayerTiming prayerTiming) {
                createArrayListOfPrayerTiming(prayerTiming);
            }
        });
    }

    private ArrayList<PrayerTimingItem> createArrayListOfPrayerTiming(PrayerTiming prayerTiming) {

        int imgId = 0;
        String name = "";
        String time = "";
        boolean reminderSet = false;

        for (int i=0;i<7;i++){
            switch (i) {
                case 0:
                    imgId = R.drawable.fajr;
                    name = getResources().getString(R.string.fajr);
                    time = prayerTiming.getFajr();
                    reminderSet = false;
                    break;

                case 1:
                    imgId = R.drawable.sunrise;
                    name = getResources().getString(R.string.sunrise);
                    time = prayerTiming.getSunsrise();
                    reminderSet = false;
                    break;

                case 2:
                    imgId = R.drawable.zohr;
                    name = getResources().getString(R.string.dhuhr);
                    time = prayerTiming.getDhuhr();
                    reminderSet = false;
                    break;

                case 3:
                    imgId = R.drawable.asr;
                    name = getResources().getString(R.string.asr);
                    time = prayerTiming.getAsr();
                    reminderSet = false;
                    break;

                case 4:
                    imgId = R.drawable.sunset;
                    name = getResources().getString(R.string.sunset);
                    time = prayerTiming.getSunset();
                    reminderSet = false;
                    break;

                case 5:
                    imgId = R.drawable.maghrib;
                    name = getResources().getString(R.string.maghrib);
                    time = prayerTiming.getMaghrib();
                    reminderSet = false;
                    break;

                case 6:
                    imgId = R.drawable.isha;
                    name = getResources().getString(R.string.isha);
                    time = prayerTiming.getIsha();
                    reminderSet = false;
                    break;

                default:
                    break;
            }
            mPrayerTimeList.add(new PrayerTimingItem(name,time,imgId,reminderSet));

            }
            return mPrayerTimeList;
        }
    }

