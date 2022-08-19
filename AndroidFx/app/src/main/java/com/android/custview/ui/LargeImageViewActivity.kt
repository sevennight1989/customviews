package com.android.custview.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.FileProvider
import com.android.custview.R
import com.android.custview.utils.ColorUtil
import com.android.zp.base.*
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.InputStream
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class LargeImageViewActivity : BaseActivity() {
    var imageView: ImageView? = null
    var imageView2: ImageView? = null
    private var mContainer: LinearLayout? = null
    override fun initData() {
        val inputStream: InputStream = assets.open("tangyan.jpg")
        val tmpOptions = BitmapFactory.Options()
        tmpOptions.inJustDecodeBounds = true
        BitmapFactory.decodeStream(inputStream, null, tmpOptions)
        val width = tmpOptions.outWidth
        val height = tmpOptions.outHeight

        val bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false)
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        val bitmap = bitmapRegionDecoder.decodeRegion(Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100), options)
        val bitmap2 = bitmapRegionDecoder.decodeRegion(Rect(0, 0, width, height), options)
        imageView!!.setImageBitmap(bitmap)
        imageView2!!.setImageBitmap(bitmap2)

    }

    override fun getLayout(): Int {
        return R.layout.layout_large_image_view
    }

    private fun viewDoc() {
        val path = "${Environment.getExternalStorageDirectory().path}/Android/data/com.android.custview/log/pateo.txt"
        KLog.logI("viewDoc = $path")
        val docFilePath = File(path)
        if (!docFilePath.parentFile.exists()) {
            docFilePath.parentFile.mkdir()
        }
        val contentUri = FileProvider.getUriForFile(baseContext, "com.android.custview.fileProvider", docFilePath)
        KLog.logI("contentUri = $contentUri")
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(contentUri, "text/plain")
        startActivity(intent)

    }

    private fun testScope(){
        GlobalScope.launch(Dispatchers.IO) {
            KLog.logI("test GlobalScope 001")
        }

        GlobalScope.launch (Dispatchers.Main){
            val image = withContext(Dispatchers.IO){
                "hello"
            }
            KLog.logI("image:$image")
            val name = withContext(Dispatchers.IO){
                "peter"
            }
            KLog.logI("name:$name")

            val finalName = getName()
            KLog.logI("finalName:$finalName")

            KLog.logI("--process--${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                KLog.logI("--end---${Thread.currentThread().name}")
            }
        }

            GlobalScope.launch {
                val avatar = async { "123" }
                val logo = async { "456" }
                val merged = "$avatar-$logo"
                KLog.logI("merged:$merged")
            }
        KLog.logI("--testScope--${Thread.currentThread().name}")

    }

    private suspend fun getName(): String {
        return withContext(Dispatchers.IO) {
            delay(1000)
            KLog.logI("sleep:${Thread.currentThread().name}")
            "Tom"
        }
    }

    override fun initView() {
        testScope()
        imageView = findViewById(R.id.img)
        imageView2 = findViewById(R.id.img2)
        mContainer = findViewById(R.id.container)
        addClassPathTitle(mContainer!!)
        addTitle(mContainer!!, "Show ImageView!")
        addSpace(mContainer!!, 100)
        addButton(mContainer!!, "ClickMe!", View.OnClickListener {
            ToastUtils.showShort("You click me , ha ha!")
            viewDoc()

        })
        mContainer!!.setBackgroundColor(ColorUtil.getMaterialColor(resources,1))

        KLog.logI("主线程id:${mainLooper.thread.id}")

//        test()
        val job = GlobalScope.launch {
            delay(3000)
            KLog.logI("协程执行结束 -- 线程id: ${Thread.currentThread().id}")
        }
        KLog.logI("协程执行结束")

    }

    fun test()= runBlocking {
        repeat(8){
            KLog.logI("协程执行$it 线程id: ${Thread.currentThread().id}")
            delay(1000)
        }
    }

    fun counter(
        dispatcher: CoroutineContext = EmptyCoroutineContext,
        start: Int,
        end: Int,
        delay: Long,
        onProgress: ((value: Int) -> Unit),
        onFinish: (() -> Unit)? = null
    ) {
        val out = flow<Int> {
            for (i in start..end) {
                emit(i)
                delay(delay)
            }
        }
        GlobalScope.launch {
            withContext(dispatcher) {
                out.collect {
                    onProgress.invoke(it)
                }
                onFinish?.invoke()
            }
        }
    }

}