package com.android.zp.base.common

interface IModelProcess<T> {
    fun getTotalCount():Int?
    fun dealPagingMode(data:List<BasePagingModel<T>>)
}