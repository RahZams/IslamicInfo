package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.databinding.SurahAyahItemLayoutBinding;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.model.SurahAyahItemApiData;
import com.example.islamicinfoapp.src.main.java.com.model.SurahData;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.SurahViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurahItemFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private String mSurahName;
    private SurahViewModel mSurahViewModel;
    private SurahItemAdapter surahItemAdapter;
    private static final String TAG = SurahItemFragment.class.getSimpleName();

    public SurahItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_surah_item, container, false);
        ButterKnife.bind(this,view);
        surahItemAdapter = new SurahItemAdapter(getActivity(),new ArrayList<SurahAyahItemApiData>());
        mSurahViewModel = ViewModelProviders.of(this).get(SurahViewModel.class);
        if (getArguments()!= null){
            mSurahName = SurahItemFragmentArgs.fromBundle(getArguments()).getSurahname();
            Log.d(Constants.SURAH_TAG, TAG + " onCreateView: " + mSurahName);
            //mSurahViewModel.fetchFromDatabase(mSurahName);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(surahItemAdapter);
            
            observeViewModel(mSurahName);
        }
        return view;
    }

    private void observeViewModel(String mSurahName) {
        Log.d(Constants.SURAH_TAG, TAG + " observeViewModel: ");
//        mSurahViewModel.mSurahDataMutableLiveData.observe(this,surahData -> {
//            Log.d("surah_item", "observeViewModel: " + surahData);
//            if (surahData!= null){
//                Log.d("surah_item", "observeViewModel: " + "not null");
//                surahItemAdapter.updateList(surahData.getAyahList());
//            }
//        });

        QuranDatabase.getInstance(getActivity()).quranDao().getSurahData(mSurahName).observe(this, new Observer<SurahData>() {
            @Override
            public void onChanged(SurahData surahData) {
                Log.d(Constants.SURAH_TAG, TAG + " onChanged: ");
                if (surahData != null){
                    Log.d(Constants.SURAH_TAG, Constants.SURAH_TAG + " onChanged: "
                            + surahData.toString().length());
                    surahItemAdapter.updateList(surahData.getAyahList());
                }
            }
        });
    }

}
