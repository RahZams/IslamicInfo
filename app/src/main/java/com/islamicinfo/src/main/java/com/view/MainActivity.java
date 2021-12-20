package com.islamicinfo.src.main.java.com.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.islamicinfo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.islamicinfo.src.main.java.com.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
   private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            if ((destination.getId() == R.id.homeFragment) || (destination.getId() == R.id.zikrFragment)
                    || (destination.getId() == R.id.pregInfoFragment) || (destination.getId() == R.id.helpFragment)){
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
            else{
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this,R.id.nav_host_fragment).navigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(Constants.PRAYER_TAG, "onBackPressed: ");
        finish();
    }
}
