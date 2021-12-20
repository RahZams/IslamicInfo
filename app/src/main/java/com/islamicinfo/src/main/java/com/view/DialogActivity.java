package com.islamicinfo.src.main.java.com.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.utilities.Utility;
import com.islamicinfo.src.main.java.com.utilities.Utility;

public class DialogActivity extends AppCompatActivity {
    TextView mDialogTitle,mDialogTimeText,mDialogDesc;
    Button mCloseButton;
    AlertDialog alertDialog;
    private static final String TAG = DialogActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAlertDialog();
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
            Log.d(Constants.PRAYER_TAG, TAG + " showAlertDialog: " + Build.VERSION.SDK_INT);
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