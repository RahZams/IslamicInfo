package com.example.islamicinfoapp.src.main.java.com.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.MoreItem;

import java.util.ArrayList;

public class MoreAdapter extends ArrayAdapter {

    ArrayList<MoreItem> more_list;
    public MoreAdapter(@NonNull Context context, int resource, ArrayList objects) {
        super(context, resource);
        more_list = objects;
    }

    @Override
    public int getCount() {
        return more_list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.more_item_layout,parent,false);
        TextView textView = convertView.findViewById(R.id.more_item_text);
        ImageView imageView = convertView.findViewById(R.id.more_item_view);
        textView.setText(more_list.get(position).getItem_name());
        imageView.setImageResource(more_list.get(position).getItem_image());
        return convertView;

    }
}
