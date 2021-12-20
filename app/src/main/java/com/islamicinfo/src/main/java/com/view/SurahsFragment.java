package com.islamicinfo.src.main.java.com.view;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurahsFragment extends Fragment {

    @BindView(R.id.expandablelistview)
    ExpandableListView mExpandableListView;

    private List<String> mSurahHeaderItemList, mSurahChildItemList;
    private HashMap<String, List<String>> mSurahChildItem;
    String mSurahName;
    private static final String TAG = SurahsFragment.class.getSimpleName();

    public SurahsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_surahs, container, false);
        ButterKnife.bind(this, view);
        readFile();
        initialiseAdapter();

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mSurahName = mSurahChildItem.get(mSurahHeaderItemList.get(groupPosition)).get(childPosition);
                //mSurahName = mSurahName.split(" ")[1];
                Log.d(Constants.SURAH_TAG, TAG + " onChildClick: " + " mSurahChildItem.get(mSurahHeaderItemList.get(groupPosition)).get(childPosition)" +
                        mSurahChildItem.get(mSurahHeaderItemList.get(groupPosition)).get(childPosition) +
                        " mSurahName: "  + mSurahName);
                NavController navController = Navigation.findNavController(v);
                SurahsFragmentDirections.ActionSurahsFragmentToSurahItemFragment action =
                        SurahsFragmentDirections.actionSurahsFragmentToSurahItemFragment(mSurahName);
                navController.navigate(action);
                NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController);
                return true;
            }
        });
        return view;
    }

    private void initialiseAdapter() {
        SurahAdapter surahAdapter = new SurahAdapter(getActivity(), mSurahHeaderItemList, mSurahChildItem);
        mExpandableListView.setAdapter(surahAdapter);
    }

    private void readFile() {
        String mTitleLine, mTextLine;
        mSurahHeaderItemList = new ArrayList<>();
        mSurahChildItem = new HashMap<>();
        try {
            InputStream inputStream = getActivity().getAssets().open("surahs.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((mTitleLine = bufferedReader.readLine()) != null &&
                    (mTextLine = bufferedReader.readLine()) != null) {
                mSurahHeaderItemList.add(mTitleLine);
                mSurahChildItemList = new ArrayList<>(Arrays.asList(mTextLine.split("\\s*,\\s*")));
                mSurahChildItem.put(mTitleLine, mSurahChildItemList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
