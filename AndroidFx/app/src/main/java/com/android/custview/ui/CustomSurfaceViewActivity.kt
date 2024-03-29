package com.android.custview.ui

import androidx.constraintlayout.widget.ConstraintLayout
import com.android.custview.R
import com.android.custview.learn.CustomSurfaceView
import com.android.zp.base.BaseActivity
import com.android.zp.base.KLog

class CustomSurfaceViewActivity : BaseActivity() {
    var surfaceView: CustomSurfaceView? = null

    override fun getLayout(): Int {
        return R.layout.activity_cust_surface_view
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
//        surfaceView = CustomSurfaceView(this)
//        val view = findViewById<ConstraintLayout>(R.id.container)
//        view.addView(surfaceView)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        KLog.logE("onBackPressed")

    }

    override fun onStop() {
        super.onStop()
        KLog.logE("CustomSurfaceViewActivity onStop")
    }


}