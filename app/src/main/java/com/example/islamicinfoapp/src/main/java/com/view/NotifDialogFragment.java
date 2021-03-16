package com.example.islamicinfoapp.src.main.java.com.view;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.islamicinfoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifDialogFragment extends DialogFragment {

    @BindView(R.id.dialog_title)
    TextView mTitleView;

    @BindView(R.id.dialog_image)
    ImageView mDialogImage;

    @BindView(R.id.dialog_time_text)
    TextView mTimeTextView;

    @BindView(R.id.dialog_desc)
    TextView mDescView;

    public NotifDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notif_dialog, container, false);
        ButterKnife.bind(this,view);
        mTitleView.setText("title");
        return view;
    }
}