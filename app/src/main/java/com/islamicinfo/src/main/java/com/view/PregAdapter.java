package com.islamicinfo.src.main.java.com.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.PregInfoItem;
import com.islamicinfo.src.main.java.com.model.PregInfoItem;

import java.util.ArrayList;

public class PregAdapter extends ArrayAdapter {

    ArrayList<PregInfoItem> preg_list;

    public PregAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        preg_list = objects;
    }

    @Override
    public int getCount() {
        return preg_list.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.pref_info_item_layout,
                parent,false);
        TextView textView = convertView.findViewById(R.id.preg_item_text);
        ImageView imageView = convertView.findViewById(R.id.preg_item_view);
        textView.setText(preg_list.get(position).getItemName());
        imageView.setImageResource(preg_list.get(position).getItemImage());
        return convertView;
    }
}
