package com.android.custview.ui

import android.app.Activity
import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.android.custview.R
import com.android.custview.adapter.ViewPagerAdapter
import com.android.custview.utils.KLog

class ViewPager2Activity : BaseActivity() {
    private var viewPager2:ViewPager2 ?= null

    override fun initData() {
        val list = ArrayList<String>()
        list.add("Page1")
        list.add("Page2")
        list.add("Page3")
        list.add("Page4")
        viewPager2!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        viewPager2!!.adapter = ViewPagerAdapter(this, list, viewPager2!!)

        val name = intent.getStringExtra("name")
        KLog.logI("name: $name")
        viewPager2!!.postDelayed({
            val intent = Intent().apply {
                putExtra("result", "from ViewPager2Activity")
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }, 3000)
    }

    override fun getLayout(): Int {
        return R.layout.activity_view_pager2
    }

    override fun initView() {
        viewPager2 = findViewById(R.id.viewpager2)
    }

}


