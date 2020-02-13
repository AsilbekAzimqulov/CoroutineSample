package com.example.coroutinessample.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinessample.ui.view_model.MainViewModel

abstract class BaseFragment(@LayoutRes resLayoutId: Int) : Fragment(resLayoutId) {

    lateinit var mainVM: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainVM = ViewModelProvider(activity!!).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedAfter()
    }

    abstract fun onViewCreatedAfter()
}