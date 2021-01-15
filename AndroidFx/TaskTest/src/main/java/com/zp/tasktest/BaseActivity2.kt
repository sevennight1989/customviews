package com.zp.tasktest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android.zp.base.ActivityInterface

open class BaseActivity2 : Activity(), ActivityInterface {
    var appActivity // 宿主的环境
            : Activity? = null

    override fun insertAppContext(appActivity: Activity) {
        this.appActivity = appActivity
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
    }

    @SuppressLint("MissingSuperCall")
    override fun onStart() {
    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
    }

    override fun setContentView(resId: Int) {
        appActivity!!.setContentView(resId)
    }

    override fun <T : View?> findViewById(id: Int): T {
        return appActivity!!.findViewById<T>(id)
    }

    override fun startActivity(intent: Intent) {
        val intentNew = Intent()
        intentNew.putExtra("className", intent.component!!.className)
        appActivity!!.startActivity(intentNew)
    }
}