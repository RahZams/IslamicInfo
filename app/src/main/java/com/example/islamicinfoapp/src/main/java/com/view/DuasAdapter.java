package com.example.islamicinfoapp.src.main.java.com.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.databinding.DuasItemLayoutBinding;
import com.example.islamicinfoapp.src.main.java.com.model.QuranDbData;
import java.util.ArrayList;
import java.util.List;

public class DuasAdapter extends RecyclerView.Adapter<DuasAdapter.DuasViewHolder> {

    private List<QuranDbData> mQuranDbData;
    //private List<String> mEveryDuasList;
    private String[] mDuasTitle,mEverydayDuasTitle;

    public DuasAdapter(ArrayList<QuranDbData> quranDbData) {
            mQuranDbData = quranDbData;
    }

    public void updateList(String[] duasTitle, List<QuranDbData> quranDbData) {
        Log.d("tag", "updateList: "  + quranDbData.size());
        mDuasTitle = duasTitle;
        mQuranDbData.addAll(quranDbData);
        Log.d("tag", "updateList: " + "mQuranDbData " + mQuranDbData.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DuasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DuasItemLayoutBinding view = DataBindingUtil.inflate(inflater,R.layout.duas_item_layout,parent,false);
        return new DuasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuasViewHolder holder, int position) {
        Log.d("tag", "onBindViewHolder: " + position);
        holder.itemView.setDua(mQuranDbData.get(position));
        holder.itemView.duaItemTitle.setText(mDuasTitle[position]);
    }

    @Override
    public int getItemCount() {
        Log.d("tag", "getItemCount: " +mQuranDbData.size() );
        return mQuranDbData.size();
    }

//    public void updateDuaList(String[] everydayDuaTitle, List<String> everydayDuas) {
//        this.mEverydayDuasTitle = everydayDuaTitle;
//        mEveryDuasList.addAll(everydayDuas);
//    }

    public class DuasViewHolder extends RecyclerView.ViewHolder{

        public DuasItemLayoutBinding itemView;
        public TextView mTextView;
        public DuasViewHolder(@NonNull DuasItemLayoutBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
            mTextView = itemView.duaItemTitle;
        }
    }
}
