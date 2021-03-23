package com.example.islamicinfoapp.src.main.java.com.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.islamicinfoapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

   private NavController navController;
//    private AppBarConfiguration appBarConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
//      appBarConfig = new AppBarConfiguration.Builder(R.id.homeFragment,
//               R.id.quranFragment,R.id.zikrFragment,R.id.pregInfoFragment,R.id.moreFragment).build();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        //NavigationUI.setupWithNavController(toolbar,navController,appBarConfig);
        //NavigationUI.setupActionBarWithNavController(this,navController,appBarConfig);



        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            //toolbar.setTitle(destination.getLabel());
            if ((destination.getId() == R.id.homeFragment) || (destination.getId() == R.id.quranFragment)
            || (destination.getId() == R.id.zikrFragment) || (destination.getId() == R.id.pregInfoFragment)
            || (destination.getId() == R.id.moreFragment)){
                bottomNavigationView.setVisibility(View.VISIBLE);
//                appBarConfig = new AppBarConfiguration.Builder(R.id.homeFragment,
//                        R.id.quranFragment,R.id.zikrFragment,R.id.pregInfoFragment,R.id.moreFragment).build();
//                NavigationUI.setupWithNavController(toolbar,navController,appBarConfig);
//                toolbar.setNavigationIcon(null);
//                navController.popBackStack(destination.getId(),false);
//                if (getSupportActionBar()!= null) {
//                    Log.d("main_log", "onCreate: getsupportactionbar not null");
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                    getSupportActionBar().setHomeButtonEnabled(false);
//                    getSupportActionBar().setDisplayShowHomeEnabled(false);
//                    toolbar.setNavigationIcon(null);
//                }
                //toolbar.setNavigationIcon(null);
                //Toast.makeText(this, "should hide", Toast.LENGTH_SHORT).show();
            }
            else{
                bottomNavigationView.setVisibility(View.GONE);
                //NavigationUI.setupWithNavController(toolbar,navController);
//                if (getSupportActionBar()!= null) {
////                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////                getSupportActionBar().setHomeButtonEnabled(true);
//                    Toast.makeText(this, "else action bar available", Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(this, "should show", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
//        if (getSupportActionBar() != null){
//            Log.d("main_log", "onSupportNavigateUp: " + "not null");
//            ActionBar actionBar = getSupportActionBar();
//            actionBar.setDisplayHomeAsUpEnabled(false);
//            actionBar.setDisplayShowHomeEnabled(false);
//        }
        return Navigation.findNavController(this,R.id.nav_host_fragment).navigateUp();

        //return NavigationUI.navigateUp(navController,appBarConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("main_log", "onBackPressed: ");
        finish();
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
