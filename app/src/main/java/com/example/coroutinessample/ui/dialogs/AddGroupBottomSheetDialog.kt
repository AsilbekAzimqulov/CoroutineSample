package com.example.coroutinessample.ui.dialogs

import androidx.lifecycle.Observer
import com.example.coroutinessample.R
import com.example.coroutinessample.room.entity.UserEntity
import com.example.coroutinessample.ui.adapters.UserAdapter
import com.example.coroutinessample.ui.base.BaseBottomSheet
import kotlinx.android.synthetic.main.dialog_add_group.*

class AddGroupBottomSheetDialog : BaseBottomSheet(R.layout.dialog_add_group) {

    private lateinit var adapter: UserAdapter

    override fun onViewCreatedAfter() {
        init()
        adapter.clickListener = {
            mainVM.addToGroup(it)
        }
    }

    private fun init() {
        adapter = UserAdapter()
        recyclerView.adapter = adapter
        mainVM.unGroupData.observe(this, dataObserver)
        mainVM.loadUnGroupData()

    }

    private val dataObserver = Observer<List<UserEntity>> {
        adapter.submitList(it)
    }

}