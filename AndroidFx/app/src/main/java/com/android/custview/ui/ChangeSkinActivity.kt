package com.android.custview.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.android.custview.R
import com.android.zp.base.SkinActivity
import com.blankj.utilcode.util.SPUtils


class ChangeSkinActivity : SkinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_skin_layout)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val isNight = SPUtils.getInstance().getBoolean("isNight", false)
        if (isNight) {
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun openChangeSkin(): Boolean {
        return true
    }

    fun dayOrNight(View: View) {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SPUtils.getInstance().put("isNight", false)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SPUtils.getInstance().put("isNight", true)
            }
        }

    }
}