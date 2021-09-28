package com.zp.sunflower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.lifecycleScope
import com.android.zp.base.KLog
import com.zp.sunflower.databinding.ActivityGardenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class GardenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
        KLog.logI("start....")

        KLog.logI("1111 ${Thread.currentThread()}")

        lifecycleScope.launch(Dispatchers.IO) {
            KLog.logI("3333 ${Thread.currentThread()}")
            withContext(Dispatchers.Main) {
                KLog.logI("4444 ${Thread.currentThread()}")
              launch(Dispatchers.IO) {
                  delay(1000)
                  KLog.logI("6666 ${Thread.currentThread()}")
              }
            }
            delay(1500)
            KLog.logI("end....")
        }
        lifecycleScope.launch(Dispatchers.Main) {
            KLog.logI("2222 ${Thread.currentThread()}")
            val rt = lifecycleScope.async { doSomethingsOne() }
            KLog.logI("rt ${rt.await()}")
        }
    }


    private suspend fun doSomethingsOne(): Int {
        KLog.logI("5555 ${Thread.currentThread()}")
        delay(1000)
        return 1001
    }
}