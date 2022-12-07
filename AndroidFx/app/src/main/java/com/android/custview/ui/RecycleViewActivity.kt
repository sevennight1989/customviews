package com.android.custview.ui

import android.Manifest
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.android.custview.R
import com.android.custview.extend.MyActivityResultContract
import com.android.zp.base.KLog
import com.android.zp.base.BaseActivity
import com.blankj.utilcode.util.ToastUtils


class RecycleViewActivity : BaseActivity() {

    private var animPercent: Float = 0f
        set(value) {
            field = value
            KLog.logI("animPercent: $animPercent")
        }

    private fun startAnim() {
        val animator = ObjectAnimator.ofFloat(this, "animPercent", 0f, 1f)
        animator.duration = 500
        animator.start()
    }


    override fun initData() {
        KLog.logI("get location permission ${hasLocationPermission(this)}")
        startAnim()
    }

    override fun getLayout(): Int {
        return R.layout.activity_recycle_view;
    }

    var img: ImageView? = null

    override fun initView() {
        img = findViewById<ImageView>(R.id.image)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
        var lin: LinearInterpolator = LinearInterpolator()
        animation.interpolator = lin
        img!!.startAnimation(animation)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.layoutManager -> {
                startActivity(Intent(this, CustLayoutManagerActivity::class.java))
                KLog.logI(android.util.Log.getStackTraceString(Throwable("Test trace log below for debug, ignore exception")))
            }
            R.id.viewpager2 -> {
                //自定义ActivityResultContract
//                mActivityLauncher.launch("From RecycleViewActivity")
                val intent = Intent(this, ViewPager2Activity::class.java).apply { putExtra("name", "From RecycleViewActivity2") }
                mActivityLauncher2.launch(intent)
            }
            R.id.request_permission -> {
                //请求当个权限
//                requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                //请求多个权限
                requestMultiplePermissions.launch(arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.WRITE_CALL_LOG,
                        Manifest.permission.CAMERA
                ))
            }
            R.id.my_activity -> {
                startActivity(Intent(this, MyActivity::class.java))
            }
            R.id.rv_diff_activity -> {
                startActivity(Intent(this, RecycleViewDiffActivity::class.java))
            }

        }
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            KLog.logI("Permission is granted")
        } else {
            KLog.logE("Permission is denied")
        }
    }

    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions: Map<String, Boolean> ->
        /*        permissions.forEach {
                    KLog.logI("${it.key} ==> ${it.value}")
                }*/
        permissions.entries.forEach {
            KLog.logI("${it.key} ===> ${it.value}")
        }

    }

    private val mActivityLauncher = registerForActivityResult(MyActivityResultContract()) { result ->
        KLog.logI(result)
        ToastUtils.showShort(result)
    }

    private val mActivityLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            var result = activityResult.data?.getStringExtra("result")
            KLog.logI(result)
            ToastUtils.showShort(result)
        }

    }

    private fun hasLocationPermission(context: Context): Boolean {
        val hasPermission = context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return hasPermission == PackageManager.PERMISSION_GRANTED
    }
}
