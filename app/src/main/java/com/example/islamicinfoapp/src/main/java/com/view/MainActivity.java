package com.example.islamicinfoapp.src.main.java.com.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.islamicinfoapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        NavigationUI.setupActionBarWithNavController(this,navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if ((destination.getId() == R.id.homeFragment) || (destination.getId() == R.id.quranFragment)
            || (destination.getId() == R.id.zikrFragment) || (destination.getId() == R.id.pregInfoFragment)
            || (destination.getId() == R.id.moreFragment)){
                bottomNavigationView.setVisibility(View.VISIBLE);
                if (getSupportActionBar()!= null) {
                    Toast.makeText(this, "if action bar available", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                }
                //toolbar.setNavigationIcon(null);
                Toast.makeText(this, "should hide", Toast.LENGTH_SHORT).show();
            }
            else{
                bottomNavigationView.setVisibility(View.GONE);
                if (getSupportActionBar()!= null) {
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                getSupportActionBar().setHomeButtonEnabled(true);
                    Toast.makeText(this, "else action bar available", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "should show", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this,R.id.nav_host_fragment).navigateUp();
        //return NavigationUI.navigateUp(navController, (DrawerLayout) null);
    }

//    @Override
//    public void onBackPressed() {
//        int count = getSupportFragmentManager().getBackStackEntryCount();
//        Toast.makeText(this, "onback pressed" + "count" + count, Toast.LENGTH_SHORT).show();
//        if (count == 0)
//            super.onBackPressed();
//        else
//            getSupportFragmentManager().popBackStack();
//    }

}
