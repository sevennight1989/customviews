package com.android.custview.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.View
import com.android.custview.R
import com.android.custview.utils.PluginManager
import com.android.zp.base.BaseActivity
import com.android.zp.base.KLog
import com.blankj.utilcode.util.ToastUtils
import java.io.File

class PluginMainActivity : BaseActivity() {
    var isPluginLoad: Boolean = false

    override fun initData() {
    }

    override fun getLayout(): Int {
        return R.layout.activity_plugin_main
    }

    override fun initView() {
    }

    fun loadPlugin(view: View) {
        if (!isPluginLoad) {
            val ret = PluginManager.getInstance(this)!!.loadPlugin()
            if (ret) {
                isPluginLoad = true
            } else {
                ToastUtils.showShort("加载Plugin失败！")
            }
        }
    }

    fun startPluginActivity(view: View) {
        if (!isPluginLoad) {
            return
        }
        val file = File("${getExternalFilesDir(null)!!.getPath()}${File.separator}p.apk")
        val path = file.absolutePath
        KLog.logI(path)
        val packageManager: PackageManager = packageManager
        val packageInfo: PackageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
        val activityInfo: ActivityInfo = packageInfo.activities[0]
        KLog.logE("activityInfo: " + activityInfo.name)
        startActivity(Intent(this, ProxyActivity::class.java).apply {
            putExtra("className", activityInfo.name)
        })
    }

}
