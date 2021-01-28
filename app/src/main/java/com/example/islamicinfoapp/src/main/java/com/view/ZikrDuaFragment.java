package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicinfoapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZikrDuaFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private List<String> mEverydayDuaTitle,mEverydayDuas;
    private ZikrDuaAdapter adapter = new ZikrDuaAdapter(new ArrayList<String>());


    public ZikrDuaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_zikr_dua, container, false);
        ButterKnife.bind(this,view);
        mEverydayDuaTitle= new ArrayList<>();
        mEverydayDuas = new ArrayList<>();
        mEverydayDuaTitle = Arrays.asList(getResources().getStringArray(R.array.dua_title));
        mEverydayDuas = Arrays.asList(getResources().getStringArray(R.array.everyday_duas));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.updateDuaList(mEverydayDuaTitle,mEverydayDuas);
        return view;
    }

}
