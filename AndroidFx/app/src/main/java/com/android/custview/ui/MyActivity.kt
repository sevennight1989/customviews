package com.android.custview.ui

import com.android.custview.R
import com.android.custview.fragment.MyFragment
import com.android.zp.base.BaseActivity

class MyActivity : BaseActivity() {

    override fun initData() {
        val transAction = supportFragmentManager.beginTransaction()
        transAction.add(R.id.my_container, MyFragment())
        transAction.commitAllowingStateLoss()

    }

    override fun getLayout(): Int {
        return R.layout.my_activity
    }

    override fun initView() {
    }
}