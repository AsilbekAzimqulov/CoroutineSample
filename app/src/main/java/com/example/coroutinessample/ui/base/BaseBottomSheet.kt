package com.example.coroutinessample.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinessample.ui.view_model.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet(@LayoutRes val layoutId: Int) : BottomSheetDialogFragment() {

    lateinit var mainVM: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainVM = ViewModelProvider(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedAfter()
    }

    abstract fun onViewCreatedAfter()

}