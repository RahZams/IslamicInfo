package com.islamicinfo.src.main.java.com.view;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.islamicinfo.R;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().
                    getPackageName(),0);
            mVersionData.setText(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return view;
    }

}
