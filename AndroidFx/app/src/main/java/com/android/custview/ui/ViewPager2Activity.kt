package com.android.custview.ui

import androidx.viewpager2.widget.ViewPager2
import com.android.custview.R
import com.android.custview.adapter.ViewPagerAdapter

class ViewPager2Activity : BaseActivity() {
    private var viewPager2:ViewPager2 ?= null

    override fun initData() {
        var list = ArrayList<String>()
        list.add("Page1")
        list.add("Page2")
        list.add("Page3")
        list.add("Page4")
        viewPager2!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        viewPager2!!.adapter = ViewPagerAdapter(this,list,viewPager2!!)
    }

    override fun getLayout(): Int {
        return R.layout.activity_view_pager2
    }

    override fun initView() {
        viewPager2 = findViewById(R.id.viewpager2)
    }

}


