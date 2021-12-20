package com.islamicinfo.src.main.java.com.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;
import com.islamicinfo.src.main.java.com.model.QuranApiData;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SurahItemAdapter extends RecyclerView.Adapter<SurahItemAdapter.SurahItemViewHolder> {

    private List<QuranApiData> mSurahData;
    private Context mContext;
    private static final String TAG = SurahItemAdapter.class.getSimpleName();

    public SurahItemAdapter(Context context, ArrayList<QuranApiData> surahData) {
        mContext = context;
        mSurahData = surahData;
    }

    @NonNull
    @Override
    public SurahItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        SurahAyahItemLayoutBinding binding = DataBindingUtil.inflate(inflater,
//                R.layout.surah_ayah_item_layout,parent,false);

        //return new SurahItemViewHolder(binding);
        return new SurahItemViewHolder(inflater.inflate(R.layout.surah_ayah_item_layout,
                parent,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull SurahItemViewHolder holder, int position) {
        SpannableString spanString;
        Log.d(Constants.SURAHITEM, TAG + " onBindViewHolder: " + position);
        //holder.itemView.setSurahitem(mSurahDataAyahs.get(position));
        Log.d(Constants.SURAHITEM, TAG + " onBindViewHolder: " +
                mSurahData.get(position).getAyahText());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            spanString = new SpannableString(mSurahData.get(position).getAyahText() + "      " +
                     Html.fromHtml("\uFD3F",Html.FROM_HTML_MODE_LEGACY) +
                    NumberFormat.getInstance(new Locale("ar","SA")).
                            format(mSurahData.get(position).getVerseNum()) +
                    Html.fromHtml("\uFD3E",Html.FROM_HTML_MODE_LEGACY));
        }
        else {
            spanString = new SpannableString(mSurahData.get(position).getAyahText() + "      " +
                    Html.fromHtml("\uFD3F") +
                    NumberFormat.getInstance(new Locale("ar","SA")).
                            format(mSurahData.get(position).getVerseNum()) +
                    Html.fromHtml("\uFD3E"));
        }

        spanString.setSpan(new ForegroundColorSpan(Color.BLACK),
                spanString.length()-3,spanString.length(),SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.ayahText.setText(spanString,TextView.BufferType.SPANNABLE);
        Log.d(Constants.SURAHITEM, TAG + " onBindViewHolder: " + "line count:" +
                holder.ayahText.getLineCount());
    }

    @Override
    public int getItemCount() {
        return mSurahData.size();
    }

    public void updateList(List<QuranApiData> surahDataAyahs) {
        Log.d(Constants.SURAHITEM, TAG + " updateList: " + surahDataAyahs.size());
        mSurahData = surahDataAyahs;
        notifyDataSetChanged();
    }

    public class SurahItemViewHolder extends RecyclerView.ViewHolder{

        public TextView ayahText;

        public SurahItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ayahText = itemView.findViewById(R.id.ayah_text);
        }
    }
}
