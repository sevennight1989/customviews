package com.android.custview.utils

import android.content.res.Resources
import android.graphics.Color
import com.android.custview.R

class ColorUtil {

    companion object {
        fun getMaterialColor(resources: Resources, index: Int): Int {
            val colors = resources.obtainTypedArray(R.array.mdcolor_300)
            val returnColor = colors.getColor(index % colors.length(), Color.BLACK)
            colors.recycle()
            return returnColor
        }
    }

}