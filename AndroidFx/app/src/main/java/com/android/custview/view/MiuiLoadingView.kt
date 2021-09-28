package com.android.custview.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin

class MiuiLoadingView @Inject constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    val CIRCULAR: Float = 60f

    var rx: Float = 0f
    var ry: Float = 0f

    var MARGIN_LEFT: Int = 150

    var centerRadiusSize: Float = 7f

    var textPaint: Paint = Paint().apply {
        textSize = 50f
        isAntiAlias = true
        color = Color.BLACK
    }

    var circlePaint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        isAntiAlias = true
        color = Color.BLACK
    }

    var centerCirclePaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = Color.BLACK
    }

    var degree = 360

    val TEXT = "正在加载中，请稍后..."

    var textHeight = 0

    init {
        val runnable = object : Runnable {
            override fun run() {
                val r = 12
                rx = MARGIN_LEFT + r * cos(degree.toDouble() * Math.PI / 180).toFloat()
                ry =
                    (measuredHeight.toFloat() / 2 + r * sin(degree.toDouble() * Math.PI / 180)).toFloat()
                invalidate()
                degree += 5
                if (degree > 360) {
                    degree = 0
                }
                postDelayed(this, 1)
            }
        }
        postDelayed(runnable, 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, 220)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val path = Path()
        path.addRoundRect(
            RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat()),
            floatArrayOf(CIRCULAR, CIRCULAR, CIRCULAR, CIRCULAR, 0f, 0f, 0f, 0f),
            Path.Direction.CW
        )
        canvas.clipPath(path)
        canvas.drawColor(Color.WHITE)

        canvas.drawCircle(
            MARGIN_LEFT.toFloat(), measuredHeight.toFloat() / 2, 35f, circlePaint
        )

        canvas.drawCircle(rx, ry, centerRadiusSize, centerCirclePaint)
        canvas.drawText(
            TEXT,
            (MARGIN_LEFT + 80).toFloat(),
            ((measuredHeight / 2) + (textHeight / 2) + 12).toFloat(),
            textPaint
        )
    }
}