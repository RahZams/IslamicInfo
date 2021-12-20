package com.islamicinfo.src.main.java.com.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.model.QuranDatabase;
import com.islamicinfo.src.main.java.com.model.QuranDbData;
import com.islamicinfo.src.main.java.com.viewmodel.DuasViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZikrFragment extends Fragment {

    @BindView(R.id.expandablelistview)
    ExpandableListView mExpandableListView;
    private List<String> mZikr_items, mSurahs_after_prayer_items, mQul_surahs;
    private HashMap<String, List<String>> mChild_items;
    private ZikrAdapter mZikrAdapter;
    private String mSurahName;
    private DuasViewModel mDuasViewModel;
    private NavController mNavController;
    private AppBarConfiguration mAppBarConfig;
    private Toolbar mToolbar;
    private static final String TAG = ZikrFragment.class.getSimpleName();

    public ZikrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zikr, container, false);
        ButterKnife.bind(this, view);
        mDuasViewModel = ViewModelProviders.of(this).get(DuasViewModel.class);
        mZikr_items = new ArrayList<>();
        mSurahs_after_prayer_items = new ArrayList<>();
        mQul_surahs = new ArrayList<>();
        mChild_items = new HashMap<>();
        mZikr_items = Arrays.asList(getActivity().getResources().getStringArray(R.array.zikr_item_names));
        mSurahs_after_prayer_items = Arrays.asList(getActivity().getResources().getStringArray(R.array.surahs_after_prayer));
        mQul_surahs = Arrays.asList(getActivity().getResources().getStringArray(R.array.qul_surahs));
        for (int i = 0; i < mZikr_items.size(); i++) {
            if (i < 5) {
                mChild_items.put(mZikr_items.get(i), null);
            } else if (i == 5) {
                mChild_items.put(mZikr_items.get(5), mQul_surahs);
            } else if (i == 6) {
                mChild_items.put(mZikr_items.get(6), mSurahs_after_prayer_items);
            }
        }
        mZikrAdapter = new ZikrAdapter(mZikr_items, mChild_items);
        mExpandableListView.setAdapter(mZikrAdapter);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mSurahName = mChild_items.get(mZikr_items.get(groupPosition)).get(childPosition);
                if (mSurahName.contains(":")){
                    Log.d(Constants.ZIKR_TAG, TAG + " onChildClick: contains" + mSurahName
                    + mSurahName.split(":")[0] + " " + mSurahName.split(":")[1]);
                    mSurahName = mSurahName.split(":")[1];
                }
//                mSurahName = mSurahName.split(" ").length == 2 ?
//                        mSurahName.split(" ")[1] : mSurahName.split(" ")[3];
                Log.d(Constants.ZIKR_TAG, TAG + " onChildClick: " + mSurahName);
                mNavController = Navigation.findNavController(view);
                ZikrFragmentDirections.ActionZikrFragmentToSurahItemFragment action =
                        ZikrFragmentDirections.actionZikrFragmentToSurahItemFragment(mSurahName);
                mNavController.navigate(action);
                return true;
            }
        });

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d(Constants.ZIKR_TAG, TAG + " onGroupClick: " + groupPosition);
                switch (groupPosition) {
                    case 0:
                        sendStringArgsToItemFrag(mZikr_items.get(groupPosition),getResources().getString(R.string.darood));
                        break;
                    case 1:
                        String str = getResources().getString(R.string.subhanallah) + "  " + getResources().getString(R.string.three_times) + "  " +
                                getResources().getString(R.string.alhamdulillah) + "  " + getResources().getString(R.string.three_times) + "  " +
                                getResources().getString(R.string.allahu_akbar) + "  " + getResources().getString(R.string.four_times);
                        sendStringArgsToItemFrag(mZikr_items.get(groupPosition),str);
                        break;
                    case 2:
                        sendStringArgsToItemFrag(mZikr_items.get(groupPosition),getResources().getString(R.string.before_anything));
                        break;
                    case 3:
                        observeViewModel(getActivity().getResources().getString(R.string.ayat),
                                mZikr_items.get(groupPosition));
                        break;
                    case 4:
                        mNavController = Navigation.findNavController(view);
                        ZikrFragmentDirections.ActionZikrFragmentToZikrDuaFragment action =
                                ZikrFragmentDirections.actionZikrFragmentToZikrDuaFragment
                                        (mZikr_items.get(groupPosition));
                        mNavController.navigate(action);
                        break;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mAppBarConfig = new AppBarConfiguration.Builder(R.id.homeFragment,R.id.zikrFragment,
                R.id.pregInfoFragment,R.id.helpFragment).build();
        mNavController = NavHostFragment.findNavController(this);
        NavigationUI.setupWithNavController(mToolbar,mNavController,mAppBarConfig);
    }

    private void sendStringArgsToItemFrag(String title, String text) {
        mNavController = Navigation.findNavController(getView());
        ZikrFragmentDirections.ActionZikrFragmentToZikrItemFragment action =
                ZikrFragmentDirections.actionZikrFragmentToZikrItemFragment(title,text);
        mNavController.navigate(action);
    }

    private void observeViewModel(String name,String str) {
        QuranDatabase.getInstance(getActivity()).quranDao().selectAllDuas(name).observe(getViewLifecycleOwner(),
                new Observer<List<QuranDbData>>() {
                    @Override
                    public void onChanged(List<QuranDbData> quranDbData) {
                        Log.d(Constants.ZIKR_TAG,TAG + " observeViewModel: " +
                                quranDbData.get(0).getQuranText());
                        if (quranDbData != null) {
                            sendStringArgsToItemFrag(str, quranDbData.get(0).quranText);
                        }
                    }
                });
    }
}
