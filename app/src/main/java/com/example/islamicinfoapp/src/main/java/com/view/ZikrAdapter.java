package com.example.islamicinfoapp.src.main.java.com.view;

import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.islamicinfoapp.R;
import java.util.HashMap;
import java.util.List;

public class ZikrAdapter extends BaseExpandableListAdapter {
    private List<String> mZikr_items;
    private HashMap<String,List<String>> mChild_items;

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
        Log.d("zikr", "getChild: " + groupPosition);
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
        Log.d("zikr", "getChildView: "  + mChild_items.get(mZikr_items.get(groupPosition)).get(childPosition));
        if (mChild_items.get(mZikr_items.get(groupPosition)).get(childPosition) != null){
            if (convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.zikr_child_item,parent,false);
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

//    private String[] mZikr_textview_items,mZikr_listview_items;
//
//    public ZikrAdapter(String[] zikr_textview_items, String[] zikr_listview_items) {
//        this.mZikr_textview_items = zikr_textview_items;
//        this.mZikr_listview_items = zikr_listview_items;
//    }
//
//    public ZikrAdapter(String[] mZikr_items) {
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == 100){
//            View view = LayoutInflater.from(parent.getContext()).
//                    inflate(R.layout.zikr_textview_item,parent,false);
//            return new ViewHolderTextView(view);
//        }
//        else if (viewType == 200){
//            View view = LayoutInflater.from(parent.getContext()).
//                    inflate(R.layout.expandablelistview_zikr_item,parent,false);
//            return new ViewHolderListView(view);
//        }
//        else
//            return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        switch (holder.getItemViewType()){
//            case 100:
//                ViewHolderTextView holderTextView = (ViewHolderTextView) holder;
//                if (position < 5){
//                    holderTextView.mTextView.setText(mZikr_textview_items[position]);
//                }
//                break;
//            case 200:
//                ViewHolderListView holderListView = (ViewHolderListView)holder;
//                break;
//                default:
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position < 5)
//            return 100;
//        else
//            return 200;
//    }
//
//    class ViewHolderTextView extends RecyclerView.ViewHolder{
//
//        TextView mTextView;
//
//        public ViewHolderTextView(@NonNull View itemView) {
//            super(itemView);
//            mTextView = itemView.findViewById(R.id.zikr_textview);
//        }
//    }
//
//    class ViewHolderListView extends RecyclerView.ViewHolder{
//
//        ExpandableListView mExpandableListView;
//
//        public ViewHolderListView(@NonNull View itemView) {
//            super(itemView);
//            mExpandableListView = itemView.findViewById(R.id.expandablelistview);
//        }
//    }
}
