package com.islamicinfo.src.main.java.com.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.islamicinfo.R
import com.islamicinfo.src.main.java.com.model.Constants

class ProgressDialogFragment : DialogFragment() {

    companion object{
        val TAG = ProgressDialogFragment::class.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(Constants.LOC_TAG, TAG + "onCreateView")
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.LOC_TAG,TAG + "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Log.d(Constants.LOC_TAG,TAG + "onStart")
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
    }
}