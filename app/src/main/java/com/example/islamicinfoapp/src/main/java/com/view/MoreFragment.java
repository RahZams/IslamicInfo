package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.MoreItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    @BindView(R.id.gridview)
    GridView gridView;

    int more_images[] = {R.drawable.ic_calendar,R.drawable.ic_settings,R.drawable.ic_help};
    ArrayList<MoreItem> more_item_list;
    String[] more_item_name;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this,view);
        more_item_list = new ArrayList<>();
        more_item_name = getResources().getStringArray(R.array.more_item_name);
        for (int i=0;i<more_item_name.length;i++){
            more_item_list.add(new MoreItem(more_item_name[i],more_images[i]));
        }
        MoreAdapter adapter = new MoreAdapter(getContext(),R.layout.more_item_layout,more_item_list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavController mNavController = Navigation.findNavController(view);
                NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(),mNavController);
                switch (position){
                    case 0:
                        mNavController.navigate(R.id.action_moreFragment_to_calendarFragment);
                        break;
                    case 1:
                        mNavController.navigate(R.id.action_moreFragment_to_settingsFragment);
                        break;
                    case 2:
                        mNavController.navigate(R.id.action_moreFragment_to_helpFragment);
                        break;
                        default:
                }
            }
        });
        return view;

    }

}
