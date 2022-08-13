package com.android.custview.learn

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class CustomSurfaceView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var flags: Boolean = false
    private val nums: MutableList<Int> = mutableListOf()
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var path: Path

    private var surfaceHolder: SurfaceHolder? = null

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
        paint.textSize = 40f
        path = Path()
        surfaceHolder = holder
        surfaceHolder?.addCallback(this)

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        flags = true
        drawCanvas()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        flags = false
    }

    private fun drawCanvas() {
        Thread {
            for (i in 0..20) {
                if (flags) {
                    val canvas = surfaceHolder?.lockCanvas()
                    nums.add(i)
                    for (num in nums) {
                        canvas?.drawText("$num", num * 50f, 50f, paint)
                    }
                    surfaceHolder?.unlockCanvasAndPost(canvas)
                }
                Thread.sleep(200)
            }
        }.start()
    }
}