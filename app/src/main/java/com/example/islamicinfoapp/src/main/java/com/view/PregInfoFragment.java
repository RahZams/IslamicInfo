package com.example.islamicinfoapp.src.main.java.com.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.PregInfoItem;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PregInfoFragment extends Fragment {
    @BindView(R.id.gridview)
    GridView gridView;
    private AppBarConfiguration mAppBarConfig;
    private NavController mNavController;

    Toolbar mToolbar;

    int[] preg_info_images = new int[]{R.drawable.ic_dua, R.drawable.ic_import_contacts_black_24dp,
            R.drawable.ic_do, R.drawable.ic_dont, R.drawable.ic_preg_info};
    ArrayList<PregInfoItem> preg_info_list;
    String[] preg_info_item_name;
    private static final String TAG = PregInfoFragment.class.getSimpleName();

    public PregInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Toast.makeText(getContext(), "onCreateView", Toast.LENGTH_SHORT).show();

        View view = inflater.inflate(R.layout.fragment_preg_info, container, false);
        ButterKnife.bind(this, view);
        preg_info_list = new ArrayList<>();
        preg_info_item_name = getResources().getStringArray(R.array.preg_info_item_name);
        for (int i = 0; i < preg_info_item_name.length; i++) {
            preg_info_list.add(new PregInfoItem(preg_info_item_name[i], preg_info_images[i]));
        }

        PregAdapter pregAdapter = new PregAdapter(getActivity(), R.layout.pref_info_item_layout, preg_info_list);
        gridView.setAdapter(pregAdapter);
//        Navigation.findNavController(view).navigate(R.id.action_pregInfoFragment_to_navigation);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(), "onitemclick", Toast.LENGTH_SHORT).show();
                Log.d(Constants.PREG_TAG,TAG +  " onItemClick: ");
                NavController mNavController = Navigation.findNavController(view);
                //Navigation.findNavController(view).navigate(R.id.action_pregInfoFragment_to_navigation);
                NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(),mNavController);
                //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Log.d(Constants.PREG_TAG,TAG +  " onItemClick: position" + position);
                switch (position) {
                    case 0:
//                        PregInfoFragmentDirections.Action_pregInfoFragment_to_duasFragment action =
//                                PregInfoFragmentDirections.action_pregInfoFragment_to_duasFragment
//                                        (getResources().getString(R.string.preg_frag_name));
                        mNavController.navigate(R.id.action_pregInfoFragment_to_duasFragment);
//                        fragmentTransaction.replace(R.id.nav_host_fragment,new DuasFragment()).
//                                addToBackStack(null).commit();
                        break;
                    case 1:
                        mNavController.navigate(R.id.action_pregInfoFragment_to_surahsFragment);
//                        fragmentTransaction.replace(R.id.nav_host_fragment,new SurahsFragment()).
//                                addToBackStack(null).commit();
                        break;
                    case 2:
                        mNavController.navigate(R.id.action_pregInfoFragment_to_doFragment);
//                        fragmentTransaction.replace(R.id.nav_host_fragment,new DoFragment()).
//                                addToBackStack(null).commit();
                        break;
                    case 3:
                        mNavController.navigate(R.id.action_pregInfoFragment_to_dontFragment);
//                        fragmentTransaction.replace(R.id.nav_host_fragment,new DontFragment()).
//                                addToBackStack(null).commit();
                        break;
                    case 4:
                        mNavController.navigate(R.id.action_pregInfoFragment_to_impPregFactsFragment);
//                        fragmentTransaction.replace(R.id.nav_host_fragment,new ImpPregFactsFragment()).
//                                addToBackStack(null).commit();
                        break;
                    default:
                }
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

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(getContext(), "onDestroy", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Toast.makeText(getContext(), "onDestroyView", Toast.LENGTH_SHORT).show();
//    }
}
