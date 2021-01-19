package com.android.zp.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.core.view.LayoutInflaterCompat
import com.android.zp.base.core.CustomAppCompatViewInflater
import com.android.zp.base.core.ViewsMatch


abstract class SkinActivity : AppCompatActivity() {

    private var viewInflater: CustomAppCompatViewInflater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutInflater: LayoutInflater = LayoutInflater.from(this)
        LayoutInflaterCompat.setFactory2(layoutInflater, this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        if (openChangeSkin()) {
            if (viewInflater == null) {

                viewInflater = CustomAppCompatViewInflater(context)
            }
            KLog.logE("Name===> ${name}")
            viewInflater!!.setName(name)
            viewInflater!!.setAttrs(attrs)
            val spinnableView = viewInflater!!.autoMatch()
            if (spinnableView != null) {
                return spinnableView;
            } else {
                return super.onCreateView(parent, name, context, attrs)
            }
        }
        return super.onCreateView(parent, name, context, attrs)
    }

    open fun openChangeSkin(): Boolean {
        return false
    }


    protected fun setDayNightMode(@NightMode nightMode: Int) {
        delegate.localNightMode = nightMode
        val decorView = window.decorView
        applyDayNightForView(decorView)
    }

    private fun applyDayNightForView(view: View) {
        if (view is ViewsMatch) {
            val viewsMatch = view as ViewsMatch
            viewsMatch.skinnableView()
        }
        if (view is ViewGroup) {
            val parent = view as ViewGroup
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                applyDayNightForView(parent.getChildAt(i))
            }
        }

    }

}