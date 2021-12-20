package com.islamicinfo.src.main.java.com.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicinfo.R
import java.lang.Exception

class GalleryFragment : Fragment() {
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
        return view
    }

    private fun initialiseAdapter() {
        val galleryAdapter = GalleryAdapter(context, list)
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
}