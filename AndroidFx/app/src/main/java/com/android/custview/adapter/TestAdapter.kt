package com.android.custview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.android.custview.R


class TestAdapter(list:ArrayList<String>) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    private val dataList: ArrayList<String> = ArrayList()

    init {
        dataList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val contentView:View = LayoutInflater.from(parent.context).inflate(R.layout.rv_001_item,parent,false)
        return TestViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.itemTv.text = dataList[position]
    }

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTv: AppCompatTextView = itemView.findViewById(R.id.rv_item_text);

    }

}