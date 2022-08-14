package com.android.custview.learn

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.android.zp.base.KLog

class CustomSurfaceView3 @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null
) : SurfaceView(context, attributeSet), SurfaceHolder.Callback {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var surfaceHolder: SurfaceHolder

    init {
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
        paint.textSize = 40f
        surfaceHolder = holder
        surfaceHolder.addCallback(this)
    }


    override fun surfaceCreated(holder: SurfaceHolder?) {
        drawCanvas()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }


    private fun drawCanvas() {
        Thread {
            while (true) {
                val canvas = surfaceHolder.lockCanvas(Rect(0, 0, 1, 1))
                val rectCanvas = canvas.clipBounds
                if (rectCanvas.height() == height && rectCanvas.width() == width) {
                    canvas.drawColor(Color.WHITE)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                } else {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                    break
                }
            }
            var canvas = surfaceHolder.lockCanvas(Rect(0,0,800,800))
            canvas.drawColor(Color.GREEN)
            KLog.logI("drawCanvas1:${canvas.clipBounds} ")
            surfaceHolder.unlockCanvasAndPost(canvas)
            Thread.sleep(500)

            canvas = surfaceHolder.lockCanvas(Rect(100, 100, 600, 600))
            canvas.drawColor(Color.BLUE)
            KLog.logI("drawCanvas2:${canvas.clipBounds} ")
            surfaceHolder.unlockCanvasAndPost(canvas)
            Thread.sleep(500)

            canvas = surfaceHolder.lockCanvas(Rect(150, 150, 500, 500))
            canvas.drawText("Hello", 200f, 200f, paint)
            KLog.logI("drawCanvas3:${canvas.clipBounds} ")
            surfaceHolder.unlockCanvasAndPost(canvas)
            Thread.sleep(500)

            canvas = surfaceHolder.lockCanvas(Rect(200, 200, 400, 400))
            canvas.drawText("Hello", 200f, 300f, paint)
            KLog.logI("drawCanvas4:${canvas.clipBounds} ")
            surfaceHolder.unlockCanvasAndPost(canvas)
            Thread.sleep(500)

        }.start()
    }



}