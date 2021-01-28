package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.DontItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DontFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    List<DontItem> mDontItemList = new ArrayList<>();


    public DontFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dont, container, false);
        ButterKnife.bind(this,view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readLine();
        initialiseAdapter();
        return view;
    }

    private void readLine() {
        String mDontTitleLine,mDontTextLine;
        mDontItemList.clear();
        try{
            InputStream inputStream = getActivity().getAssets().open("dont_preg.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            while ((mDontTitleLine = bufferedReader.readLine()) != null &&
                    (mDontTextLine = bufferedReader.readLine()) != null){
                mDontItemList.add(new DontItem(mDontTitleLine,mDontTextLine));
            }
            inputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void initialiseAdapter() {
        DontAdapter dontAdapter = new DontAdapter(getActivity(),mDontItemList);
        mRecyclerView.setAdapter(dontAdapter);
    }


}
