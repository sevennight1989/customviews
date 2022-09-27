package com.android.zp.base.common

interface IPagingCallback {
    fun scrollEnd()
    fun scrollRefresh()
    fun scrolling()
}