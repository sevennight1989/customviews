package com.android.zp.base

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import com.android.zp.base.view.ClassPathInfoTextView

fun BaseActivity.addTitle(parent: LinearLayout, text: String) {
    val textView = TextView(this)
    textView.textSize = 14f
    textView.text = text
    val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    lp.leftMargin = 30
    lp.rightMargin = 30
    lp.topMargin = 24
    lp.bottomMargin = 24
    parent.addView(textView, lp)
}

fun BaseActivity.addClassPathTitle(parent: LinearLayout) {
    val textView = ClassPathInfoTextView(this)
    textView.textSize = 14f
    val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    lp.leftMargin = 30
    lp.rightMargin = 30
    lp.topMargin = 24
    lp.bottomMargin = 24
    parent.addView(textView, lp)
}

fun BaseActivity.addButton(parent: LinearLayout, text: String, onClickListener: View.OnClickListener): Button {
    val button = Button(this)
    button.isAllCaps = false
    button.text = text
    button.setOnClickListener(onClickListener)
    val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    lp.leftMargin = 20
    lp.rightMargin = 20
    button.minHeight = 150
    parent.addView(button, lp)
    return button
}

fun BaseActivity.addSpace(parent: LinearLayout, height: Int) {
    val space = Space(this)
    parent.addView(space, ViewGroup.LayoutParams.MATCH_PARENT, height)
}