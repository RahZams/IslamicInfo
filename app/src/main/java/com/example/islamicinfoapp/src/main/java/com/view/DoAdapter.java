package com.example.islamicinfoapp.src.main.java.com.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.Constants;
import com.example.islamicinfoapp.src.main.java.com.model.DoItem;

import java.util.List;

class DoAdapter extends RecyclerView.Adapter<DoAdapter.DoItemViewHolder> {

    private static final String TAG = DoAdapter.class.getSimpleName();
    private Context mContext;
    private List<DoItem> mDoItemList;

    public DoAdapter(Context context, List<DoItem> mDoItemsList) {
        this.mContext = context;
        this.mDoItemList = mDoItemsList;
    }

    @NonNull
    @Override
    public DoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.do_list_item_layout,parent,false);
        return new DoItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoItemViewHolder holder, int position) {
        DoItem doItem = mDoItemList.get(position);
        holder.mDoTitle.setText(doItem.getmDoItemTitle());
        //holder.mDoText.setText(doItem.getmDoItemText());

        //holder.mDoText.setVisibility(View.GONE);

//        if (mCurrentPosition == position){
//            //Animation slideDown = AnimationUtils.loadAnimation(mContext,R.anim.slide_down);
//            holder.mDoText.setVisibility(View.VISIBLE);
//            //holder.mDoText.startAnimation(slideDown);
//        }

        holder.mDoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.PREG_TAG,TAG +  " onClick: " + "holder mdotitle onclick" + holder.mDoText.getVisibility());
                if (holder.mDoText.getVisibility() == View.GONE) {
                    Log.d(Constants.PREG_TAG,TAG + " gone");
                    holder.mDoText.setText(doItem.getmDoItemText());
                    holder.mDoText.setVisibility(View.VISIBLE);
                    holder.mDoTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                            R.drawable.ic_arrow_drop_up, 0);
                }
                else if (holder.mDoText.getVisibility() == View.VISIBLE){
                    Log.d(Constants.PREG_TAG,TAG +  " onClick: " + " visible");
                    holder.mDoText.setVisibility(View.GONE);
                    holder.mDoTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                            R.drawable.ic_arrow_drop_down, 0);
                }
                //notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDoItemList.size();
    }

    class DoItemViewHolder extends RecyclerView.ViewHolder{

        TextView mDoTitle,mDoText;

        public DoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mDoTitle = itemView.findViewById(R.id.do_item_title);
            mDoText = itemView.findViewById(R.id.do_item_text);
        }
    }
}
