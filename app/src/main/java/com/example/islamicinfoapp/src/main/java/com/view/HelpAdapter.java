package com.example.islamicinfoapp.src.main.java.com.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.HelpItem;

import java.util.ArrayList;

public class HelpAdapter extends ArrayAdapter {
    private ArrayList<HelpItem> mHelpItems;

    public HelpAdapter(@NonNull Context context, int resource, ArrayList objects) {
        super(context, resource);
        mHelpItems = objects;
    }

    @Override
    public int getCount() {
        return mHelpItems.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.help_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.help_item_text);
        textView.setText(mHelpItems.get(position).getItem_name());
        textView.setCompoundDrawablesWithIntrinsicBounds(mHelpItems.get(position).getItem_image(), 0, 0, 0);

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "text" + textView.getText(), Toast.LENGTH_SHORT).show();
//                switch (position) {
//                    case 0:
//
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    default:
//
//                }
//            }
//        });

        return convertView;
    }
}
