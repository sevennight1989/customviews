package com.android.zp.base.common

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

interface IPagingList<T> {
    fun bindView(context: Context, lifecycleOwner: LifecycleOwner, recyclerView: RecyclerView, adapter: PagingAdapter<T>, mode: ListMode)

    fun bindData(model: List<BasePagingModel<T>>)
}