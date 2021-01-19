package com.android.zp.base.core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatViewInflater
import androidx.appcompat.widget.ActionBarOverlayLayout
import com.android.zp.base.view.*


class CustomAppCompatViewInflater(context: Context) : AppCompatViewInflater() {

    private var name: String = ""
    private var context: Context? = null
    private var attrs: AttributeSet? = null

    init {
        this.context = context
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setAttrs(attrs: AttributeSet) {
        this.attrs = attrs
    }

    fun autoMatch(): View?{
        var view: View? = null
        when (name) {
            "LinearLayout" -> {
                // view = super.createTextView(context, attrs); // 源码写法
                view = SkinnableLinearLayout(context, attrs)
                verifyNotNull(view, name)
            }
            "RelativeLayout" -> {
                view = SkinnableRelativeLayout(context, attrs)
                verifyNotNull(view, name)
            }
            "TextView" -> {
                view = SkinnableTextView(context!!, attrs)
                verifyNotNull(view, name)
            }
            "ImageView" -> {
                view = SkinnableImageView(context, attrs)
                verifyNotNull(view, name)
            }
            "Button" -> {
                view = SkinnableButton(context, attrs)
                verifyNotNull(view, name)
            }
        }
        return view
    }

    private fun verifyNotNull(view: View?, name: String) {
        if (view == null) {
            throw IllegalStateException("${javaClass.name} asked to inflate view for <$name>, but returned null")
        }
    }
}