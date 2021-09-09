package com.example.islamicinfoapp.src.main.java.com.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicinfoapp.R
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {
    val MAX_IMAGE_SIZE = 200

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_gallery, container, false)
        gal_recyclerview?.layoutManager = GridLayoutManager(activity,2)
        val galleryadapter = GalleryAdapter(context,getItemsList())
        gal_recyclerview?.adapter = galleryadapter
        return view
    }

    private fun getItemsList(): ArrayList<Bitmap> {
        val list = ArrayList<Bitmap>()
        list.add(scaleDown(R.drawable.prayer_1,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.prayer_2,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.prayer_3,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.mecca_1,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.mecca_2,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.mecca_3,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.mecca_4,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.mecca_5,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.madina_1,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.madina_2,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.madina_3,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.madina_4,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.madina_5,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.preg_1,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.preg_2,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.preg_3,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.preg_4,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.preg_5,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.preg_6,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.preg_7,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.eid_1,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.eid_2,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.eid_3,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.eid_4,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.eid_5,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.quote_1,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.quote_2,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.quote_3,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.quote_4,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.quote_5,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.quote_6,MAX_IMAGE_SIZE,true))
        list.add(scaleDown(R.drawable.quote_7,MAX_IMAGE_SIZE,true))
        return list
    }

    private fun scaleDown(image: Int, maxImageSize: Int, b: Boolean): Bitmap {
        val bitmap = BitmapFactory.decodeResource(resources,image)
        return Bitmap.createScaledBitmap(bitmap,maxImageSize,maxImageSize,b)
    }
}