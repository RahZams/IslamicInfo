package com.islamicinfo.src.main.java.com.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.islamicinfo.R
import com.example.islamicinfo.databinding.GalleryItemBinding

class GalleryAdapter(val context: Context?, val images_list: ArrayList<String>)
    :RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):GalleryViewHolder {
        return GalleryViewHolder(GalleryItemBinding.inflate(LayoutInflater.from(context)))
//        return GalleryViewHolder(LayoutInflater.from(context).
//        inflate(R.layout.gallery_item, parent, false))
    }

    override fun onBindViewHolder(holder:GalleryViewHolder, position: Int) {
        if (context != null){
            Glide.with(context)
                    .load(images_list.get(position))
                    .placeholder(R.drawable.ic_no_image)
                    .error(R.drawable.ic_no_image)
                    .override(200,200)
                    .into(holder.image)
        }
    }

    override fun getItemCount(): Int {
        return images_list.size
    }

    class GalleryViewHolder(binding:GalleryItemBinding):RecyclerView.ViewHolder(binding.root){
        val image = binding.itemImage
    }
}

//class GalleryAdapter(val context: Context?, val list:ArrayList<Bitmap>)
//    : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
//
//    val TAG = GalleryAdapter::class.simpleName
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
//        Log.d(Constants.PREG_TAG, "$TAG onCreateViewHolder: ")
//        return GalleryViewHolder(LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false))
//
//    }
//
//    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
//        Log.d(Constants.PREG_TAG, "$TAG onBindViewHolder: $position")
//        holder.image.setImageBitmap(list.get(position))
//    }
//
//    override fun getItemCount(): Int {
//        Log.d(Constants.PREG_TAG, "$TAG getItemCount: ${list.size}")
//        return list.size
//    }
//
//    class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val image = view.item_image
//    }
//}