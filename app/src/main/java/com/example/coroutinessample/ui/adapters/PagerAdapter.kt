package com.example.coroutinessample.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coroutinessample.ui.screens.AddUserScreen
import com.example.coroutinessample.ui.screens.GroupUserScreen
import com.example.coroutinessample.ui.screens.UserScreen

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserScreen()
            1 -> GroupUserScreen()
            else -> AddUserScreen()
        }
    }


}