package com.android.custview.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.android.custview.R
import hei.permission.PermissionActivity

class SplashActivity : PermissionActivity(), PermissionActivity.CheckPermListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission()
        } else {
            startMain()
        }
        findViewById<Button>(R.id.retry_rq).setOnClickListener(View.OnClickListener {
            requestPermission()
        })
    }

    override fun superPermission() {
        startMain()
    }

    private fun requestPermission() {
        checkPermission(
            this,
            R.string.perm_tip,
//            Manifest.permission.ACCESS_NETWORK_STATE,
//            Manifest.permission.INTERNET,
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}