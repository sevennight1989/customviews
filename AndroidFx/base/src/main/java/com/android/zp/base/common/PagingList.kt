package com.android.zp.base.common

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class PagingList<T> :IPagingList<T>,IModelProcess<T>,LifecycleEventObserver {

    private var mTotalScroll = 0
    private var mCallback: IPagingCallback? = null
    private var currentPageIndex = ""

    override fun getTotalCount(): Int? {
        TODO("Not yet implemented")
    }

    override fun dealPagingMode(data: List<BasePagingModel<T>>) {
        TODO("Not yet implemented")
    }

    override fun bindView(context: Context, lifecycleOwner: LifecycleOwner, recyclerView: RecyclerView, adapter: PagingAdapter<T>, mode: ListMode) {
        TODO("Not yet implemented")
    }

    override fun bindData(model: List<BasePagingModel<T>>) {
        TODO("Not yet implemented")
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        TODO("Not yet implemented")
    }

}