package com.android.zp.base.model

import android.content.res.TypedArray
import android.util.SparseIntArray

class AttrsBean {
    private var resourcesMap: SparseIntArray? = null

    companion object {
        private val DEFAULT_VALUE: Int = -1
    }

    init {
        resourcesMap = SparseIntArray()
    }

    fun saveViewResource(trpedArray: TypedArray, styleable: IntArray) {
        for (i in 0 until trpedArray.length()) {
            val key = styleable[i]
            val resourceId = trpedArray.getResourceId(i, DEFAULT_VALUE)
            resourcesMap?.put(key, resourceId)
        }
    }

    fun getViewResource(styleable: Int): Int? {
        return resourcesMap?.get(styleable)
    }

}