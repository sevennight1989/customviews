package com.android.custview.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import com.android.custview.utils.PluginManager
import com.android.zp.base.ActivityInterface
import com.android.zp.base.KLog


class ProxyActivity : Activity(){

    override fun getResources(): Resources? {
        return PluginManager.getInstance(this)!!.resources
    }


    override fun getClassLoader(): ClassLoader? {
        return PluginManager.getInstance(this)!!.classLoader
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val className: String? = intent.getStringExtra("className")
        var mPluginActivityClass = classLoader!!.loadClass(className)
        val constructor = mPluginActivityClass.getConstructor(*arrayOf())
        val mPluginActivity = constructor.newInstance(*arrayOf())
        var activityInterface: ActivityInterface = mPluginActivity as ActivityInterface;
        // 注入
        activityInterface.insertAppContext(this);
        var bundle:Bundle  = Bundle().apply { putString("appName", "我是宿主传递过来的信息"); }
        // 执行插件里面的onCreate方法
        activityInterface.onCreate(bundle);
    }

    override fun startActivity(intent: Intent?) {
        KLog.logI("ProxyActivity startActivity")
        val className = intent!!.getStringExtra("className")
        val proxyIntent = Intent(this, ProxyActivity::class.java)
        proxyIntent.putExtra("className", className) // 包名+TestActivity
        // 要给TestActivity 进栈
        super.startActivity(proxyIntent)
    }
}