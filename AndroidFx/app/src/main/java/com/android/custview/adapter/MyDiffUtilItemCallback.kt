package com.android.custview.adapter

import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.DiffUtil
import com.android.custview.bean.TestBean

class MyDiffUtilItemCallback : DiffUtil.ItemCallback<TestBean>() {

    override fun areItemsTheSame(oldItem: TestBean, newItem: TestBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TestBean, newItem: TestBean): Boolean {
        return TextUtils.equals(oldItem.name, newItem.name)
    }

    override fun getChangePayload(oldItem: TestBean, newItem: TestBean): Any? {
        val payload = Bundle()
        if (!TextUtils.equals(oldItem.name, newItem.name)) {
            payload.putString("KEY_NAME", newItem.name)
        }
        if (payload.size() == 0) {
            return null
        }
        return payload
    }
}