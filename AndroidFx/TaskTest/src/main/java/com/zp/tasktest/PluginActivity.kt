package com.zp.tasktest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class PluginActivity : BaseActivity2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plugin_main)
        Toast.makeText(appActivity, "我是插件", Toast.LENGTH_SHORT).show();
        findViewById<Button>(R.id.start_task).setOnClickListener {
            startActivity(Intent(appActivity, TaskActivity::class.java))
        }
    }
}