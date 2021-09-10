package com.example.islamicinfoapp.src.main.java.com.view

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicinfoapp.R
import com.example.islamicinfoapp.src.main.java.com.model.Constants
import kotlinx.android.synthetic.main.gallery_item.view.*

class GalleryAdapter(val context: Context?, val list:ArrayList<Bitmap>)
    : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    val TAG = GalleryAdapter::class.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        Log.d(Constants.PREG_TAG,"$TAG onCreateViewHolder: ")
        return GalleryViewHolder(LayoutInflater.from(context).
        inflate(R.layout.gallery_item,parent,false))

    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        Log.d(Constants.PREG_TAG,"$TAG onBindViewHolder: $position")
        holder.image.setImageBitmap(list.get(position))
    }

    override fun getItemCount(): Int {
        Log.d(Constants.PREG_TAG,"$TAG getItemCount: ${list.size}")
        return list.size
    }

    class GalleryViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image = view.item_image
    }

}