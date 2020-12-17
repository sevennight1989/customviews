package com.android.custview.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.custview.R
import com.android.custview.adapter.TestAdapter
import com.android.custview.layout.MyLayoutManager

class CustLayoutManagerActivity : BaseActivity() {
    private var rv01: RecyclerView? = null
    override fun initData() {
    }

    override fun getLayout(): Int {
        return R.layout.activity_cust_layout_manager
    }

    override fun initView() {
        rv01 = findViewById(R.id.rv_001)
        val list:ArrayList<String> = ArrayList()
        for(i in 1..30){
            list.add("Item : $i")
        }
        val adapter = TestAdapter(list)
//        rv01!!.layoutManager = LinearLayoutManager(this)
        rv01!!.layoutManager = MyLayoutManager()
        rv01!!.adapter = adapter
    }

}
