package com.android.custview.ui

import android.content.Intent
import android.view.View
import com.android.custview.R
import com.android.zp.base.BaseActivity

class CustomSurfaceViewList : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_cust_surface_view_list
    }

    override fun initView() {
    }

    override fun initData() {
    }

    @Override
    fun onClick(v: View) {
        val intent = Intent()
        when (v.id) {
            R.id.custom_surface_view -> {
                intent.setClass(this, CustomSurfaceViewActivity::class.java)

            }
            R.id.custom_surface_view2 -> {
                intent.setClass(this, CustomSurfaceViewActivity2::class.java)
            }
        }
        startActivity(intent)
    }
}