package com.android.custview.utils

import android.annotation.SuppressLint
import android.content.Context
import com.blankj.utilcode.util.SPUtils

class ConfigUtils(private val context: Context) {

    private val DEMO_MAX_LIFE_CYCLE: String = "demo_max_life_cycle"

    fun initDemoConfigs() {
        SPUtils.getInstance().put(DEMO_MAX_LIFE_CYCLE, false)
    }

    fun isEnableDemoMaxLifeCycle():Boolean{
        return SPUtils.getInstance().getBoolean(DEMO_MAX_LIFE_CYCLE,false)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var configUtils: ConfigUtils? = null
        fun getInstance(context: Context): ConfigUtils? {
            if (configUtils == null) {
                synchronized(ConfigUtils::class.java) {
                    if (configUtils == null) {
                        configUtils = ConfigUtils(context)
                    }
                }
            }
            return configUtils
        }
    }
}