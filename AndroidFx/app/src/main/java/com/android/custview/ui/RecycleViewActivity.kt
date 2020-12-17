package com.android.custview.ui

import android.content.Intent
import android.view.View
import com.android.custview.R
import com.android.custview.utils.KLog


class RecycleViewActivity : BaseActivity() {

    override fun initData() {
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
                KLog.logI("Custom layout...")
            }

        }
    }

}
