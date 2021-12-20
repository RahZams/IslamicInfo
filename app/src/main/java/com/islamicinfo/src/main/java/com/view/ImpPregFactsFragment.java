package com.islamicinfo.src.main.java.com.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.PregImpFactsItem;
import com.islamicinfo.src.main.java.com.model.PregImpFactsItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImpPregFactsFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private List<PregImpFactsItem> mPregImpFactsItemList =  new ArrayList<>();


    public ImpPregFactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_imp_preg_facts, container, false);
        ButterKnife.bind(this,view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readFile();
        initiliseAdapter();
        return view;
    }

    private void readFile(){
        String mTitleLIne,mTextLine;
        mPregImpFactsItemList.clear();
        try {
            InputStream mInputStream = getActivity().getAssets().open("imp_facts.txt");
            BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));

            while((mTitleLIne = mBufferedReader.readLine()) != null &&
                    (mTextLine = mBufferedReader.readLine()) != null){
               mPregImpFactsItemList.add(new PregImpFactsItem(mTitleLIne,mTextLine));
            }
            mInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initiliseAdapter() {
        PregImpFactsAdapter pregImpFactsAdapter = new PregImpFactsAdapter(getActivity(),mPregImpFactsItemList);
        mRecyclerView.setAdapter(pregImpFactsAdapter);
    }

}
