package com.example.coroutinessample.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.coroutinessample.R
import com.example.coroutinessample.extensions.hideKeyboard
import com.example.coroutinessample.extensions.hideSoftKeyboard
import com.example.coroutinessample.ui.adapters.PagerAdapter
import com.example.coroutinessample.ui.view_model.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var mainVM: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        mainVM.scrollPager.observe(this, scrollObserver)
    }

    private fun init() {
        mainVM = ViewModelProvider(this).get(MainViewModel::class.java)
        pagerAdapter = PagerAdapter(this)
        pager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.all_user)
                1 -> getString(R.string.group)
                else -> getString(R.string.add_user)
            }
        }.attach()
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                hideSoftKeyboard()
            }
        })
    }

    private val scrollObserver = Observer<Boolean> {
        pager.setCurrentItem(0, true)
        mainVM.clearUI()
    }
}
