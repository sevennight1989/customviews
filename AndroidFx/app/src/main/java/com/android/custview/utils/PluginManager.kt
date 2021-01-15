package com.android.custview.utils

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Environment
import android.util.Log
import com.android.zp.base.KLog
import dalvik.system.DexClassLoader
import java.io.File


class PluginManager(private val context: Context) {
    private var dexClassLoader: DexClassLoader? = null
    var resources: Resources? = null
        private set

    /**
     * （2.1 Activity class，  2.2layout）
     * 加载插件
     */
    fun loadPlugin() :Boolean{
        try {
            val file = File( context.getExternalFilesDir(null)!!.getPath().toString() + File.separator + "p.apk")
            if (!file.exists()) {
                KLog.logE("插件包 不存在...")
                return false
            }
            val pluginPaht = file.absolutePath
            /**
             * 下面是加载插件里面的 class
             */
// dexClassLoader需要一个缓存目录   /data/data/当前应用的包名/pDir
            val fileDir = context.getDir("pDir", Context.MODE_PRIVATE)
            // Activity class
            dexClassLoader = DexClassLoader(pluginPaht, fileDir.absolutePath, null, context.classLoader)
            /**
             * 下面是加载插件里面的layout
             */
// 加载资源
            val assetManager = AssetManager::class.java.newInstance()
            // 我们要执行此方法，为了把插件包的路径 添加进去
// public final int addAssetPath(String path)
            val addAssetPathMethod = assetManager.javaClass.getMethod("addAssetPath", String::class.java) // 他是类类型 Class
            addAssetPathMethod.invoke(assetManager, pluginPaht) // 插件包的路径   pluginPaht
            val r = context.resources // 宿主的资源配置信息
            // 特殊的 Resources，加载插件里面的资源的 Resources
            resources = Resources(assetManager, r.displayMetrics, r.configuration) // 参数2 3  资源配置信息
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    val classLoader: ClassLoader?
        get() = dexClassLoader

    companion object {
        private val TAG = PluginManager::class.java.simpleName
        private var pluginManager: PluginManager? = null
        fun getInstance(context: Context): PluginManager? {
            if (pluginManager == null) {
                synchronized(PluginManager::class.java) {
                    if (pluginManager == null) {
                        pluginManager = PluginManager(context)
                    }
                }
            }
            return pluginManager
        }
    }

}