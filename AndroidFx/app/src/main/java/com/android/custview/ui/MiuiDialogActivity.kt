package com.android.custview.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.custview.R
import com.android.custview.databinding.ActivityMiuiDialogBinding
import com.android.custview.view.MiuiLoadingDialog

class MiuiDialogActivity : AppCompatActivity() {

    private var dialog: MiuiLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMiuiDialogBinding>(
            this, R.layout.activity_miui_dialog
        )
        dialog = MiuiLoadingDialog(this)


        binding.setClickListener { view ->
            when (view) {
                binding.showDialog -> {
                    if (!dialog!!.isShowing) {
                        dialog!!.show()
                    }

                }
                binding.hideDialog -> {
                    if (dialog!!.isShowing) {
                        dialog!!.hide()
                    }
                }
                else -> {

                }
            }
        }
    }
}