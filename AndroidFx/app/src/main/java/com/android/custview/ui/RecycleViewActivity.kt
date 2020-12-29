package com.android.custview.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import com.android.custview.R
import com.android.custview.utils.KLog


class RecycleViewActivity : BaseActivity() {

    override fun initData() {
        KLog.logI("get location permission ${hasLocationPermission(this)}")
    }

    override fun getLayout(): Int {
        return R.layout.activity_recycle_view;
    }

    override fun initView() {

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.layoutManager -> {
                startActivity(Intent(this,CustLayoutManagerActivity::class.java))
                KLog.logI( android.util.Log.getStackTraceString(Throwable("Test trace log below for debug, ignore exception")))
            }
            R.id.viewpager2->{
                startActivity(Intent(this,ViewPager2Activity::class.java))
            }

        }
    }

    private fun hasLocationPermission(context: Context): Boolean {
        val hasPermission = context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return hasPermission == PackageManager.PERMISSION_GRANTED
    }
}
