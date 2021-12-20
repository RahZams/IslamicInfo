package com.islamicinfo.src.main.java.com.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.DontItem;
import com.islamicinfo.src.main.java.com.model.DontItem;

import java.util.List;

public class DontAdapter extends RecyclerView.Adapter<DontAdapter.DontViewHolder> {

    private List<DontItem> mDontItemList;
    private Context mContext;


    public DontAdapter(Context context, List<DontItem> mDontItemList) {
        this.mContext = context;
        this.mDontItemList = mDontItemList;

    }

    @NonNull
    @Override
    public DontViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.dont_list_item_layout,parent,false);
        return new DontViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DontViewHolder holder, int position) {
        DontItem dontItem = mDontItemList.get(position);
        holder.mDontItemTitle.setText(dontItem.getmDontItemTitle());

        holder.mDontItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mDontItemText.getVisibility() == View.GONE){
                    holder.mDontItemText.setText(dontItem.getmDontItemText());
                    holder.mDontItemText.setVisibility(View.VISIBLE);
                    holder.mDontItemTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,
                            R.drawable.ic_arrow_drop_up,0);
                }
                else if (holder.mDontItemText.getVisibility() == View.VISIBLE){
                    holder.mDontItemText.setVisibility(View.GONE);
                    holder.mDontItemTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,
                            R.drawable.ic_arrow_drop_down,0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDontItemList.size();
    }

    class DontViewHolder extends RecyclerView.ViewHolder{

        TextView mDontItemTitle,mDontItemText;

        public DontViewHolder(@NonNull View itemView) {
            super(itemView);
            mDontItemTitle = itemView.findViewById(R.id.dont_title);
            mDontItemText = itemView.findViewById(R.id.dont_text);
        }
    }
}
