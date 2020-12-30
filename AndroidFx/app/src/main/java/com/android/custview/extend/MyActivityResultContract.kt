package com.android.custview.extend

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.android.custview.ui.ViewPager2Activity

class MyActivityResultContract : ActivityResultContract<String, String>() {
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, ViewPager2Activity::class.java).apply {
            putExtra("name", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        val data = intent?.getStringExtra("result")
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else "invalid data"
    }

}