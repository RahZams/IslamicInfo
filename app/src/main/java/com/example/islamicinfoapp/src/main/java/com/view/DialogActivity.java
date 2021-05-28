package com.example.islamicinfoapp.src.main.java.com.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.utilities.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;

public class DialogActivity extends AppCompatActivity {
    TextView mDialogTitle,mDialogTimeText,mDialogDesc;
    Button mCloseButton;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_dialog);
        //getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
        showAlertDialog();
        //displayAlertDialog();
    }

    private void displayAlertDialog(){
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showAlertDialog() {
        Utility.playSound(DialogActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.fragment_notif_dialog,viewGroup,false);
        mDialogTitle = view.findViewById(R.id.dialog_title);
        mDialogTimeText = view.findViewById(R.id.dialog_time_text);
        mDialogDesc = view.findViewById(R.id.dialog_desc);
        mCloseButton = view.findViewById(R.id.closeButton);
        mDialogTitle.setText(getIntent().getStringExtra(getResources().getString(R.string.dialogTitle)));
        mDialogTimeText.setText(getIntent().getStringExtra(getResources().getString(R.string.namazTime)));
        mDialogDesc.setText(getIntent().getStringExtra(getResources().getString(R.string.dialogdesc)));
        builder.setView(view);
        builder.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("prayer", "showAlertDialog: " + Build.VERSION.SDK_INT);
            view.setClipToOutline(true);
        }
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog.isShowing()){
                    alertDialog.dismiss();
                    finish();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.curve_shaped));
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}