package com.android.custview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.custview.R

class ViewPagerAdapter(context: Context, data: ArrayList<String>, viewpager2: ViewPager2) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    private val mData: ArrayList<String> = ArrayList()

    private var mContext: Context? = null

    private var mViewPager2: ViewPager2? = null

    private var colorArray: IntArray = intArrayOf(android.R.color.black, android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_red_dark)

    init {
        mData.addAll(data)
        mContext = context
        mViewPager2 = viewpager2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = mData[position]
        holder.myTextview.text = animal
        holder.constraintLayout.setBackgroundResource(colorArray[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myTextview = itemView.findViewById<AppCompatTextView>(R.id.tvTitle)
        var constraintLayout = itemView.findViewById<ConstraintLayout>(R.id.container)

    }
}