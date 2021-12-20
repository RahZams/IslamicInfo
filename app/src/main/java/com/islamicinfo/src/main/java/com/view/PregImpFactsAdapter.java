package com.islamicinfo.src.main.java.com.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.PregImpFactsItem;
import com.islamicinfo.src.main.java.com.model.PregImpFactsItem;

import java.util.List;

public class PregImpFactsAdapter extends RecyclerView.Adapter<PregImpFactsAdapter.PregImpFactsViewHolder> {
    private Context mContext;
    private List<PregImpFactsItem> mPregImpFactsItemList;

    public PregImpFactsAdapter(Context context, List<PregImpFactsItem> mPregImpFactsItemList) {
        this.mContext = context;
        this.mPregImpFactsItemList = mPregImpFactsItemList;
    }

    @NonNull
    @Override
    public PregImpFactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.preg_imp_facts_list_item_layout,
                parent,false);
        return new PregImpFactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PregImpFactsViewHolder holder, int position) {
        PregImpFactsItem pregImpFactsItem = mPregImpFactsItemList.get(position);
        holder.mImpFactsItemTitle.setText(pregImpFactsItem.getmDoItemTitle());

        holder.mImpFactsItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mImpFactsItemText.getVisibility() == View.GONE){
                    holder.mImpFactsItemText.setText(pregImpFactsItem.getmDoItemText());
                    holder.mImpFactsItemText.setVisibility(View.VISIBLE);
                    holder.mImpFactsItemTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,
                            R.drawable.ic_arrow_drop_up,0);
                }
                else if (holder.mImpFactsItemText.getVisibility() == View.VISIBLE){
                    holder.mImpFactsItemText.setVisibility(View.GONE);
                    holder.mImpFactsItemTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,
                            R.drawable.ic_arrow_drop_down,0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPregImpFactsItemList.size();
    }

    class PregImpFactsViewHolder extends RecyclerView.ViewHolder{

        TextView mImpFactsItemTitle,mImpFactsItemText;

        public PregImpFactsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImpFactsItemTitle = itemView.findViewById(R.id.preg_imp_facts_title);
            mImpFactsItemText = itemView.findViewById(R.id.preg_imp_facts_text);
        }
    }
}
