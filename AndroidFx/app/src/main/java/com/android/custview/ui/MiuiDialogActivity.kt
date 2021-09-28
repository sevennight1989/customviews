package com.android.custview.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.custview.R
import com.android.custview.databinding.ActivityMiuiDialogBinding
import com.android.custview.view.MiuiLoadingDialog
import com.android.zp.base.utils.RoundRect

class MiuiDialogActivity : AppCompatActivity() {

    private var dialog: MiuiLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMiuiDialogBinding>(
            this, R.layout.activity_miui_dialog
        )
        dialog = MiuiLoadingDialog(this)
        val roundRect = RoundRect(
            200, 200, 50f
        )
        val bitmap: Bitmap = roundRect.toRoundRect(this, R.mipmap.touxiang)
        binding.image1.setImageBitmap(bitmap)

        val roundRect2 = RoundRect(
            200, 200, 100f
        )
        val bitmap2: Bitmap = roundRect2.toRoundRect(this, R.mipmap.touxiang)
        binding.image2.setImageBitmap(bitmap2)

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