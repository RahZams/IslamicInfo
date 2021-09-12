package com.example.islamicinfoapp.src.main.java.com.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicinfoapp.R
import java.io.IOException
import java.lang.Exception

class GalleryFragment : Fragment() {
    //val MAX_IMAGE_SIZE = 200
    var list = ArrayList<String>()
    val TAG = GalleryFragment::class.simpleName
    lateinit var g_recyclerview:RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        g_recyclerview = view.findViewById(R.id.gal_recyclerview)
        g_recyclerview.layoutManager = GridLayoutManager(context,2)
        readFileAndFillArrayList()
        initialiseAdapter()
//        val galleryadapter = GalleryAdapter(context,getItemsList())
//        g_recyclerview.adapter = galleryadapter
        return view
    }

    private fun initialiseAdapter() {
        val galleryAdapter = GalleryAdapter(context,list)
        g_recyclerview.adapter = galleryAdapter

    }

    private fun readFileAndFillArrayList() {
        try {
            context?.assets?.open("images_link_address.txt")
                    ?.bufferedReader()?.forEachLine { list.add(it) }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

//    private fun getItemsList(): ArrayList<Bitmap> {
//
//        val list = ArrayList<Bitmap>()
//        Log.d(Constants.PREG_TAG, "$TAG getItemsList: ${list.size}"  )
//        list.add(scaleDown(R.drawable.prayer_1,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.prayer_2,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.prayer_3,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.mecca_1,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.mecca_2,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.mecca_3,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.mecca_4,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.mecca_5,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.madina_1,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.madina_2,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.madina_3,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.madina_4,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.madina_5,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.preg_1,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.preg_2,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.preg_3,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.preg_4,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.preg_5,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.preg_6,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.preg_7,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.eid_1,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.eid_2,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.eid_3,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.eid_4,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.eid_5,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.quote_1,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.quote_2,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.quote_3,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.quote_4,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.quote_5,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.quote_6,MAX_IMAGE_SIZE,true))
//        list.add(scaleDown(R.drawable.quote_7,MAX_IMAGE_SIZE,true))
//        Log.d(Constants.PREG_TAG, "$TAG getItemsList: ${list.size}"  )
//        return list
//    }
//
//    private fun scaleDown(image: Int, maxImageSize: Int, b: Boolean): Bitmap {
//        val bitmap = BitmapFactory.decodeResource(resources,image)
//        return Bitmap.createScaledBitmap(bitmap,maxImageSize,maxImageSize,b)
//    }
}