package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islamicinfoapp.BuildConfig;
import com.example.islamicinfoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppInfoFragment extends Fragment {

    @BindView(R.id.version_data)
    TextView mVersionData;

    public AppInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_app_info, container, false);
        ButterKnife.bind(this,view);
        mVersionData.setText(BuildConfig.VERSION_NAME);
        return view;
    }

}
