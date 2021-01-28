package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.DoItem;

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
public class DoFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    List<DoItem> mDoItemsList = new ArrayList<>();


    public DoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_do, container, false);
        ButterKnife.bind(this,view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readFile();
        initialiseAdapter();
        return view;
    }

    private void readFile() {
        String mTitleLine,mTextLine;
        mDoItemsList.clear();
        try{
            InputStream inputStream = getActivity().getAssets().open("do_preg.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            while ((mTitleLine = bufferedReader.readLine())!= null && (mTextLine = bufferedReader.readLine())!= null){
                mDoItemsList.add(new DoItem(mTitleLine,mTextLine));
            }

            inputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initialiseAdapter() {
        DoAdapter mdoAdapter = new DoAdapter(getActivity(),mDoItemsList);
        mRecyclerView.setAdapter(mdoAdapter);

    }




}
