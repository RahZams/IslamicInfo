package com.example.islamicinfoapp.src.main.java.com.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.islamicinfoapp.R;

public class DialogActivity extends AppCompatActivity {
    TextView mDialogTitle,mDialogTimeText,mDialogDesc;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        showAlertDialog();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.fragment_notif_dialog,viewGroup,false);
        mDialogTitle = view.findViewById(R.id.dialog_title);
        mDialogTimeText = view.findViewById(R.id.dialog_time_text);
        mDialogDesc = view.findViewById(R.id.dialog_desc);
        mDialogTitle.setText(getIntent().getStringExtra(getResources().getString(R.string.dialogTitle)));
        mDialogTimeText.setText(getIntent().getStringExtra(getResources().getString(R.string.namazTime)));
        mDialogDesc.setText(getIntent().getStringExtra(getResources().getString(R.string.dialogdesc)));
        builder.setView(view);
        builder.setNeutralButton(getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (alertDialog.isShowing()){
                    alertDialog.dismiss();
                    finish();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();

    }


}