package com.android.custview.ui

import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.custview.R
import com.android.custview.adapter.MyListAdapter
import com.android.custview.bean.TestBean
import com.android.zp.base.BaseActivity
import com.android.zp.base.KLog

class RecycleViewDiffActivity : BaseActivity() {

    var recyclerView: RecyclerView? = null
    var myListAdapter: MyListAdapter? = null
    var add_btn:Button?= null


    override fun getLayout(): Int {
        return R.layout.activity_recycle_view_diff
    }

    override fun initView() {
        recyclerView = findViewById(R.id.rv)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        myListAdapter = MyListAdapter(this)
        recyclerView?.adapter = myListAdapter
        add_btn = findViewById(R.id.add_data)
        add_btn?.setOnClickListener {
            addData()
        }
    }

    override fun initData() {
        prepareData()
    }

    private fun prepareData() {
        val list = ArrayList<TestBean>()
        for (i in 0..10) {
            list.add(TestBean(i, "item $i"))
        }
        myListAdapter?.setData(list)
        KLog.logI("prepareData done!")
    }

    private fun addData() {
        val list = ArrayList<TestBean>()
        for (i in 10..20) {
            list.add(TestBean(i, "item $i"))
        }
        myListAdapter?.addData(list)
        KLog.logI("addData done!")
    }

    private fun updateData() {
        val list = ArrayList<TestBean>()
        for (i in 20..30) {
            list.add(TestBean(i, "item $i"))
        }
        myListAdapter?.addData(list)
        KLog.logI("updateData done!")
    }

}