package com.android.custview.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.android.custview.R
import com.android.custview.utils.ColorUtil
import com.android.zp.base.*
import com.blankj.utilcode.util.ToastUtils
import java.io.InputStream

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

    override fun initView() {
        imageView = findViewById(R.id.img)
        imageView2 = findViewById(R.id.img2)
        mContainer = findViewById(R.id.container)
        addClassPathTitle(mContainer!!)
        addTitle(mContainer!!, "Show ImageView!")
        addSpace(mContainer!!, 100)
        addButton(mContainer!!, "ClickMe!", View.OnClickListener {
            ToastUtils.showShort("You click me , ha ha!")
        })
        mContainer!!.setBackgroundColor(ColorUtil.getMaterialColor(resources,1))
    }

}