package com.example.islamicinfoapp.src.main.java.com.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.islamicinfoapp.R;

public class AlertDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        displayAlertDialog();
    }

    private void displayAlertDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog,viewGroup,false);
        TextView title = view.findViewById(R.id.dialog_title);
        ImageView imageView = view.findViewById(R.id.dialog_image);
        TextView time_text = view.findViewById(R.id.dialog_time_text);
        TextView desc = view.findViewById(R.id.dialog_desc);
        title.setText(getIntent().getStringExtra(getResources().getString(R.string.dialogTitle)));
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_quran));
        time_text.setText(getIntent().getStringExtra(getResources().getString(R.string.namazName)));
        desc.setText(getIntent().getStringExtra(getResources().getString(R.string.dialogdesc)));
        builder.setView(view);
        builder.setNeutralButton(getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}