package com.android.custview.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import javax.inject.Inject

class MiuiLoadingDialog @Inject constructor(context: Context) : Dialog(context) {

    private var miuiLoadingView: MiuiLoadingView = MiuiLoadingView(context)

    init {
        setContentView(miuiLoadingView)
        setCancelable(true)
    }

    override fun show() {
        super.show()
        val window: Window? = window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp.height = 200
        window.attributes = wlp
    }
}