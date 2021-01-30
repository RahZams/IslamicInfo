package com.example.islamicinfoapp.src.main.java.com.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.databinding.ActivitySplashBinding;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDao;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDatabase;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.DuasViewModel;
import com.example.islamicinfoapp.src.main.java.com.viewmodel.SurahViewModel;

public class SplashActivity extends AppCompatActivity {
    private DuasViewModel mDuasViewModel;
    private SurahViewModel mSurahViewModel;
    private AsyncTask<Void,Void,Void> mduasDeleteTask;
    private QuranDao mQuranDao;
    private Utility utility =  new Utility();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        binding.splashImage.setImageResource(R.drawable.prayer_times);
        mDuasViewModel = ViewModelProviders.of(this).get(DuasViewModel.class);
        mSurahViewModel = ViewModelProviders.of(this).get(SurahViewModel.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, LocationActivity.class);
                mduasDeleteTask =  new DeleteTask();
                mduasDeleteTask.execute();
                startActivity(mainIntent);

            }
        },10*1000);

    }

    private class DeleteTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mQuranDao = QuranDatabase.getInstance(getApplication()).quranDao();
            mQuranDao.deleteAllQuranData();
            mQuranDao.deleteAllSurahData();
            mQuranDao.deletePrayerTimeData(utility.getCurrentDate());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mDuasViewModel.fetchFromRemote();
            mSurahViewModel.fetchFromRemote();
        }
    }
}
