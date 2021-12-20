package com.islamicinfo.src.main.java.com.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.utilities.LocListener;
import com.islamicinfo.src.main.java.com.utilities.SharedPrefsHelper;
import com.islamicinfo.src.main.java.com.utilities.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationActivity extends AppCompatActivity implements LocListener.CallMainActivityCallback {

    @BindView(R.id.location_text)
    Button mLocationText;
    private Utility utility = new Utility();
    private LocListener mLocListener;
    private ProgressDialogFragment dialog;
    public static final int LOCATION_REQUEST_CODE = 1;
    private static final String TAG = LocationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        dialog = new ProgressDialogFragment();
        mLocationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                Log.d(Constants.LOC_TAG,
                        TAG +  " onClick: ");
                mLocListener = new LocListener(LocationActivity.this);
                if (utility.checkForNetworkAvailibility(LocationActivity.this)){
                    if(utility.checkForLocationConnection(LocationActivity.this)){
                        requestPermission();
                    }
                    else{
                        mLocListener.showAlertDialog(LocationActivity.this,R.drawable.ic_location_off,
                                getResources().getString(R.string.location_dialog_title),
                                getResources().getString(R.string.location_alert_message),
                                getResources().getString(R.string.ok_button_text));
                    }
                }
                else{
                    mLocListener.showAlertDialog(LocationActivity.this,R.drawable.ic_no_network
                    ,getResources().getString(R.string.no_internet),getResources().getString(R.string.no_internet_message),
                            getResources().getString(R.string.ok_button_text));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LocListener.LOCATION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )
                        == PackageManager.PERMISSION_GRANTED){
                    //disable the button so that it cannot be pressed again
                    mLocationText.setEnabled(false);
                    Log.d(Constants.LOC_TAG,TAG +  " onRequestPermissionsResult: " + "granted");
                    dialog.show(getSupportFragmentManager(),ProgressDialogFragment.Companion.getTAG());
                    mLocListener.findLocation();
                }
            }
            else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale
                        (this,Manifest.permission.ACCESS_FINE_LOCATION)){
                    Log.d(Constants.LOC_TAG,TAG +  " onRequestPermissionsResult: " + "denied");
                    mLocListener.showAlertDialog(LocationActivity.this,0,getResources().getString(R.string.location_perm_denied)
                    ,getResources().getString(R.string.loc_explanation_message),getResources().getString(R.string.retry));
                }
                else{
                    Log.d(Constants.LOC_TAG,TAG + " onRequestPermissionsResult: " +
                            "dont ask again" + grantResults[0]);
                    SharedPrefsHelper.storeValue(this,getResources().getString(R.string.loc_permission),
                            getResources().getString(R.string.dont_ask_again));
                    mLocListener.onLocationChanged(null);
                }
            }
        }
    }

    @Override
    public void callMainAcitivity(String city, String country) {
        Log.d(Constants.LOC_TAG,TAG +  " callMainAcitivity: " + city + country);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(LocationActivity.this, MainActivity.class);
            intent.putExtra(getString(R.string.cityname),city);
            intent.putExtra(getString(R.string.countryname),country);
            LocationActivity.this.finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            dialog.dismiss();
            startActivity(intent);
        }

    public void requestPermission() {

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }
        else{
            Log.d(Constants.LOC_TAG,TAG +  "request permission" + "else");
            dialog.show(getSupportFragmentManager(),ProgressDialogFragment.Companion.getTAG());
            mLocListener.findLocation();
        }
    }
}
