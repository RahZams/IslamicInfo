package com.islamicinfo.src.main.java.com.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicinfo.R;

import java.util.ArrayList;
import java.util.List;

class ZikrDuaAdapter extends RecyclerView.Adapter<ZikrDuaAdapter.ZikrDuaViewHolder> {

    private List<String> mDuaList,mEverydayDuaTitle;

    public ZikrDuaAdapter(ArrayList<String> duaList) {
        this.mDuaList = duaList;
        this.mEverydayDuaTitle = new ArrayList<>();
    }

    @NonNull
    @Override
    public ZikrDuaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zikr_dua_item,parent,false);
        return new ZikrDuaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZikrDuaViewHolder holder, int position) {
        holder.mDuaTitle.setText(mEverydayDuaTitle.get(position));
        holder.mDuaText.setText(mDuaList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDuaList.size();
    }

    public void updateDuaList(List<String> everydayDuaTitle, List<String> everydayDuas) {
        mDuaList.addAll(everydayDuas);
        mEverydayDuaTitle.addAll(everydayDuaTitle);
        notifyDataSetChanged();

    }

    public class ZikrDuaViewHolder extends RecyclerView.ViewHolder{

        public TextView mDuaTitle,mDuaText;

        public ZikrDuaViewHolder(@NonNull View itemView) {
            super(itemView);
            mDuaTitle = itemView.findViewById(R.id.zikr_item_title);
            mDuaText = itemView.findViewById(R.id.urdu_dua);
        }
    }
}
