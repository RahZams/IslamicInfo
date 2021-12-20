package com.islamicinfo.src.main.java.com.view;

import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;

import java.util.HashMap;
import java.util.List;

public class ZikrAdapter extends BaseExpandableListAdapter {
    private List<String> mZikr_items;
    private HashMap<String,List<String>> mChild_items;
    private static final String TAG = ZikrAdapter.class.getSimpleName();

    public ZikrAdapter(List<String> mZikr_items, HashMap<String, List<String>> mChild_items) {
        this.mZikr_items = mZikr_items;
        this.mChild_items = mChild_items;
    }

    @Override
    public int getGroupCount() {
        return mZikr_items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition > 4) {
            return mChild_items.get(mZikr_items.get(groupPosition)).size();
        }
        else return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mZikr_items.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d(Constants.ZIKR_TAG, TAG + " getChild: " + groupPosition);
        if (groupPosition > 4)
            return mChild_items.get(mZikr_items.get(groupPosition)).get(childPosition);
        else
            return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.zikr_group_item,parent,false);
        }

        TextView textView = convertView.findViewById(R.id.zikr_group_item);
        textView.setText(mZikr_items.get(groupPosition));
        textView.setTypeface(null, Typeface.BOLD);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d(Constants.ZIKR_TAG, TAG + " getChildView: "  + mChild_items.get(mZikr_items.get(groupPosition)).get(childPosition));
        if (mChild_items.get(mZikr_items.get(groupPosition)).get(childPosition) != null){
            if (convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.zikr_child_item,parent,false);
            }
            TextView textView = convertView.findViewById(R.id.zikr_child_item);
            textView.setText(mChild_items.get(mZikr_items.get(groupPosition)).get(childPosition));
            return convertView;
        }
        else
            return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
