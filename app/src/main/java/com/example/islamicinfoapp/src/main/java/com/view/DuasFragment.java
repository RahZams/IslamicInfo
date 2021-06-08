package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDbData;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.DuasViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DuasFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private DuasAdapter adapter = new DuasAdapter(new ArrayList<QuranDbData>());
    private DuasViewModel mDuasViewModel;
    private String[] mDuasTitle,mEverydayDuaTitle;
    private String mFrag_name;
    private List<String> mEverydayDuas;
    private static final String TAG = DuasFragment.class.getSimpleName();

    public DuasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duas, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        mDuasViewModel = ViewModelProviders.of(this).get(DuasViewModel.class);
        mDuasTitle = getActivity().getResources().getStringArray(R.array.duas_title);
        //mDuasViewModel.fetchFromRemote();
        mDuasViewModel.fetchFromDatabase(getContext().getResources().getString(R.string.duaName));
        observeViewModel();
//        if (getArguments() != null){
//            mFrag_name = DuasFragmentArgs.fromBundle(getArguments()).getCalling_frag_name();
//            if (mFrag_name == getResources().getString(R.string.preg_frag_name)){
//                mDuasViewModel = ViewModelProviders.of(this).get(DuasViewModel.class);
//                mDuasTitle = getActivity().getResources().getStringArray(R.array.duas_title);
//                //mDuasViewModel.fetchFromRemote();
//                mDuasViewModel.fetchFromDatabase(getContext().getResources().getString(R.string.duaName));
//                observeViewModel();
//            }
//            else if (mFrag_name == getResources().getString(R.string.zikr_frag_name)){
//                mEverydayDuaTitle = getResources().getStringArray(R.array.dua_title);
//                mEverydayDuas = new ArrayList<>();
//                mEverydayDuas = Arrays.asList(getResources().getStringArray(R.array.everyday_duas));
//                adapter.updateDuaList(mEverydayDuaTitle,mEverydayDuas);
//            }
//        }
        return view;
    }

    private void observeViewModel() {
        mDuasViewModel.duasLiveData.observe(this, quranDbData -> {
            Log.d(Constants.PRAYER_TAG, TAG + "observeViewModel: " + quranDbData.size());
            if (quranDbData != null) {
                Log.d(Constants.PRAYER_TAG, TAG + " observeViewModel: " + quranDbData.get(0).getQuranText());
                adapter.updateList(mDuasTitle, quranDbData);
            }
        });
    }
}
