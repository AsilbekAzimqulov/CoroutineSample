package com.example.coroutinessample.ui.screens

import android.view.View
import androidx.lifecycle.Observer
import com.example.coroutinessample.R
import com.example.coroutinessample.room.entity.UserEntity
import com.example.coroutinessample.ui.adapters.UserAdapter
import com.example.coroutinessample.ui.base.BaseFragment
import com.example.coroutinessample.ui.dialogs.AddGroupBottomSheetDialog
import kotlinx.android.synthetic.main.screen_group_user.*
import kotlinx.android.synthetic.main.screen_user.recyclerView

class GroupUserScreen : BaseFragment(R.layout.screen_group_user) {
    private lateinit var adapter: UserAdapter

    override fun onViewCreatedAfter() {
        init()
        setAction()
    }

    private fun setAction() {
        adapter.removeListener = {
            mainVM.delete(it)
        }
        addGroupButton.setOnClickListener {
            openBottomSheet()
        }
    }

    private fun init() {
        adapter = UserAdapter()
        recyclerView.adapter = adapter
        mainVM.groupData.observe(this, groupObserver)
        mainVM.goneFAB.observe(this, visibleGone)
        mainVM.loadGroupData()
    }

    private fun openBottomSheet() {
        val bottomSheet = AddGroupBottomSheetDialog()
        bottomSheet.show(childFragmentManager, "")
    }

    private val groupObserver = Observer<List<UserEntity>> {
        adapter.submitList(it)
    }
    private val visibleGone = Observer<Boolean> {
        if (it) addGroupButton.visibility = View.GONE
        else addGroupButton.visibility = View.VISIBLE
    }
}