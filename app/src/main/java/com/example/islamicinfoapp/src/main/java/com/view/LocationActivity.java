package com.example.islamicinfoapp.src.main.java.com.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.utilities.LocListener;
import com.example.islamicinfoapp.src.main.java.com.utilities.SharedPrefsHelper;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationActivity extends AppCompatActivity implements LocListener.CallMainActivityCallback{

    @BindView(R.id.location_text)
    Button mLocationText;
    private Utility utility = new Utility();
    private LocListener mLocListener;
    private static final String TAG = LocationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        mLocationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.LOC_TAG,TAG +  " onClick: ");
                mLocListener = new LocListener(LocationActivity.this);
                if (utility.checkForNetworkAvailibility(LocationActivity.this)){
                    if(utility.checkForLocationConnection(LocationActivity.this)){
                        mLocListener.requestPermission();
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
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationActivity.this);
//                    builder.setIcon(R.drawable.ic_no_network);
//                    builder.setTitle("No internet connection");
//                    builder.setMessage("You are offline.Please check your internet connection");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            mLocListener.redirectingToProvideConnection();
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.setCancelable(false);
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                        @Override
//                        public void onShow(DialogInterface dialog) {
//                            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
//                            .setTextColor(getResources().getColor(R.color.colorPrimary));
//                        }
//                    });
//                    alertDialog.show();
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
                    Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
                    mLocListener.findLocation();
                }
            }
            else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale
                        (this,Manifest.permission.ACCESS_FINE_LOCATION)){
                    Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
                    mLocListener.showAlertDialog(LocationActivity.this,0,getResources().getString(R.string.location_perm_denied)
                    ,getResources().getString(R.string.loc_explanation_message),getResources().getString(R.string.retry));
                }
                else{
                    Log.d(TAG, "onRequestPermissionsResult: " + grantResults[0]);
                    Toast.makeText(this, "dont ask again", Toast.LENGTH_SHORT).show();
                    SharedPrefsHelper.storeValue(this,getResources().getString(R.string.loc_permission),
                            getResources().getString(R.string.dont_ask_again));
//                    Intent intent = new Intent(this,MainActivity.class);
//                    intent.putExtra(getResources().getString(R.string.dont_ask_again),getResources().getString(R.string.dont_ask_again));
//                    startActivity(intent);
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
            startActivity(intent);
        }
}
