package com.android.zp.base.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class PagingAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var datas: List<BasePagingModel<T>>? = null
    private var maps: MutableMap<String, MutableList<BasePagingModel<T>>>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return buildBusinessHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (datas != null) {
            bindBusinessData(holder, position, datas)
        } else if (maps != null) {
            bindBusinessMapData(holder, position, maps)
        }
    }

    abstract fun getHolderWidth(context: Context): Int

    override fun getItemCount(): Int {
        return if (datas != null) datas!!.size else 0
    }

    open fun bindBusinessMapData(holder: RecyclerView.ViewHolder, position: Int, maps: MutableMap<String, MutableList<BasePagingModel<T>>>?) {

    }

    open fun bindBusinessData(holder: RecyclerView.ViewHolder, position: Int, datas: List<BasePagingModel<T>>?) {

    }

    abstract fun buildBusinessHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    @SuppressLint("NotifyDataSetChanged")
    fun setPagingData(datas: List<BasePagingModel<T>>){
        this.datas = datas
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPagingMapData(maps:MutableMap<String,MutableList<BasePagingModel<T>>>){
        this.maps = maps
        notifyDataSetChanged()
    }
}