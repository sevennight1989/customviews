package com.android.custview.learn

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.android.zp.base.KLog

class CustomSurfaceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var path: Path

    private var surfaceHolder: SurfaceHolder? = null;

    private var flag: Boolean = false

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        path = Path()
        surfaceHolder = holder
        surfaceHolder?.addCallback(this)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                KLog.logI("Touch ${event.x}, ${event.y}")
                path.moveTo(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
            }
        }

        return super.onTouchEvent(event)
    }

    private fun drawCanvas() {
        Thread {
            while (flag) {
                val canvas = surfaceHolder?.lockCanvas()
                canvas?.drawPath(path, paint)
                surfaceHolder?.unlockCanvasAndPost(canvas)
                Thread.sleep(10)
            }
        }.start()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        KLog.logI("surfaceCreated")
        flag = true
        drawCanvas()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        KLog.logI("surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        KLog.logI("surfaceDestroyed")
        flag = false
    }

}