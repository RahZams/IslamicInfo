package com.islamicinfo.src.main.java.com.view;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.model.QuranDatabase;
import com.islamicinfo.src.main.java.com.model.QuranDbData;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DuasFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private DuasAdapter adapter = new DuasAdapter(new ArrayList<QuranDbData>());
    private String[] mDuasTitle;
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
        mDuasTitle = getActivity().getResources().getStringArray(R.array.duas_title);
        observeViewModel();
        return view;
    }

    private void observeViewModel() {
        // get data from database and pass it on to adapter to update the view
        QuranDatabase.getInstance(getActivity()).quranDao().
                selectAllDuas(getContext().getResources().getString(R.string.duaName)).
                observe(getViewLifecycleOwner(), new Observer<List<QuranDbData>>() {
            @Override
            public void onChanged(List<QuranDbData> quranDbData) {
                if (quranDbData != null){
                    Log.d(Constants.ZIKR_TAG, TAG + " observeViewModel: " + quranDbData.get(0).getQuranText());
                    adapter.updateList(mDuasTitle,quranDbData);
                }
            }
        });
    }
}
