package com.example.islamicinfoapp.src.main.java.com.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.islamicinfoapp.R;

import java.util.HashMap;
import java.util.List;


public class SurahAdapter extends BaseExpandableListAdapter{

    private Context mContext;
    private List<String> mSurahHeaderItemList;
    private HashMap<String,List<String>> mSurahChildItem;

    public SurahAdapter(Context context, List<String> surahHeaderItemList, HashMap<String, List<String>> surahChildItem) {
        this.mContext = context;
        this.mSurahHeaderItemList = surahHeaderItemList;
        this.mSurahChildItem = surahChildItem;
    }

    @Override
    public int getGroupCount() {
        return mSurahHeaderItemList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mSurahChildItem.get(mSurahHeaderItemList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mSurahHeaderItemList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mSurahChildItem.get(mSurahHeaderItemList.get(groupPosition)).get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String mTtitle = mSurahHeaderItemList.get(groupPosition);
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.surah_list_item_layout,parent,false);
        }
        TextView surahTitleView  = convertView.findViewById(R.id.surah_item_title);
        surahTitleView.setTypeface(null, Typeface.BOLD);
        surahTitleView.setText(mTtitle);
        return convertView;
    }





    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String mChildText = mSurahChildItem.get(mSurahHeaderItemList.get(groupPosition)).get(childPosition);
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.surah_list_child_item,parent,false);
        }
        TextView mChildTextView = convertView.findViewById(R.id.surah_child_item_text);
        //mChildTextView.setTypeface(null,Typeface.BOLD);
        mChildTextView.setText(mChildText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

//public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {

//    private Context mContext;
//    private List<SurahItem> mSurahItemsList;
//
//    public SurahAdapter(Context context, List<SurahItem> mSurahItemsList) {
//        this.mContext = context;
//        this.mSurahItemsList = mSurahItemsList;
//
//    }
//
//    @NonNull
//    @Override
//    public SurahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.surah_list_item_layout,parent,false);
//        return new SurahViewHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull SurahViewHolder holder, int position) {
//        SurahItem surahItem = mSurahItemsList.get(position);
//        Toast.makeText(mContext, "title" +surahItem.getmSurahItemTitle() , Toast.LENGTH_SHORT).show();
//        holder.mSurahItemTitle.setText(surahItem.getmSurahItemTitle());
//
//        holder.mSurahItemTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        Toast.makeText(mContext, "mSurahItemsList.size()" + mSurahItemsList.size(), Toast.LENGTH_SHORT).show();
//        return mSurahItemsList.size();
//
//    }
//
//    class SurahViewHolder extends RecyclerView.ViewHolder{
//
//        TextView mSurahItemTitle;
//        ListView mSurahSubItemList;
//
//        public SurahViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mSurahItemTitle = itemView.findViewById(R.id.surah_item_title);
//            //mSurahSubItemList = itemView.findViewById(R.id.surah_sub_item_list);
//        }
//    }
//}
