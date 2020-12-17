package com.android.custview.layout

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {

        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (itemCount == 0 || state!!.isPreLayout) {
            return
        }
        for (i in 0 until itemCount) {
            val child: View = recycler!!.getViewForPosition(i)
            measureChildWithMargins(child, 0, 0)
            addView(child)
            layoutDecoratedWithMargins(child, getDecoratedMeasuredWidth(child) * i, 0, getDecoratedMeasuredWidth(child) * (i + 1), getDecoratedMeasuredHeight(child))
        }
    }
}