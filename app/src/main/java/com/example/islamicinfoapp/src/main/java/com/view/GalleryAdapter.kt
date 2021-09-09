package com.example.islamicinfoapp.src.main.java.com.view

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicinfoapp.R
import kotlinx.android.synthetic.main.gallery_item.view.*

class GalleryAdapter(val context: Context?, val list:ArrayList<Bitmap>)
    : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(LayoutInflater.from(context).
        inflate(R.layout.gallery_item,parent,false))

    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.image.setImageBitmap(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class GalleryViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image = view.item_image
    }

}