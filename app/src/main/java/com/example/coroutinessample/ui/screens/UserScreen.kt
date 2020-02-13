package com.example.coroutinessample.ui.screens

import androidx.lifecycle.Observer
import com.example.coroutinessample.R
import com.example.coroutinessample.room.entity.UserEntity
import com.example.coroutinessample.ui.adapters.UserAdapter
import com.example.coroutinessample.ui.base.BaseFragment
import kotlinx.android.synthetic.main.screen_user.*

class UserScreen : BaseFragment(R.layout.screen_user) {
    private lateinit var adapter: UserAdapter

    override fun onViewCreatedAfter() {
        init()
        adapter.removeListener = {
            mainVM.delete(it)
        }
    }

    private fun init() {
        adapter = UserAdapter()
        recyclerView.adapter = adapter
        mainVM.allData.observe(this, observer)
        mainVM.loadData()
    }


    private val observer = Observer<List<UserEntity>> {
        adapter.submitList(it)
    }

}