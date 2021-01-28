package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islamicinfoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZikrItemFragment extends Fragment {

    @BindView(R.id.zikr_item)
    TextView textView;


    public ZikrItemFragment() {

    }
        // Required empty public co]nstructor



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zikr_item, container, false);
        ButterKnife.bind(this,view);
        if (getArguments() != null){
            textView.setText(ZikrItemFragmentArgs.fromBundle(getArguments()).getZikrText());
        }
        return view;
    }

}
