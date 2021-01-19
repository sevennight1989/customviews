package com.android.zp.base.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.android.zp.base.R
import com.android.zp.base.core.ViewsMatch
import com.android.zp.base.model.AttrsBean

class SkinnableTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppCompatTextView(context, attrs, defStyleAttr), ViewsMatch {

    private var attrsBean: AttrsBean? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, android.R.attr.textViewStyle)

    init {
        attrsBean = AttrsBean()
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableTextView, defStyleAttr, 0)
        attrsBean?.saveViewResource(typedArray, R.styleable.SkinnableTextView)
        typedArray.recycle()
    }

    override fun skinnableView() {
        var key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_background]
        val backgroundResourceId = attrsBean?.getViewResource(key)
        if (backgroundResourceId!! > 0) {
            val drawable: Drawable? = ContextCompat.getDrawable(context, backgroundResourceId)
            setBackgroundDrawable(drawable)
        }
        key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_textColor]
        val textColorResourceId = attrsBean?.getViewResource(key)
        if (textColorResourceId!! > 0) {
            val color: ColorStateList? = ContextCompat.getColorStateList(context, textColorResourceId)
            setTextColor(color)
        }
    }

}