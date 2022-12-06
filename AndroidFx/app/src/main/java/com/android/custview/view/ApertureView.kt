package com.android.custview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.graphics.withSave
import com.blankj.utilcode.util.ConvertUtils

class ApertureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    companion object {
        var DEF_WIDTH = ConvertUtils.dp2px(200.0f).toFloat()
        var DEF_HEIGHT = ConvertUtils.dp2px(200.0f).toFloat()
        private var RADIUS = ConvertUtils.dp2px(20.0f).toFloat()
    }

    private val animator by lazy {
        val animator = ObjectAnimator.ofFloat(this, "currentSpeed", 0f, 360f)
        animator.repeatCount = -1
        animator.interpolator = null
        animator.duration = 2000L
        animator
    }

    init {
        setBackgroundColor(Color.YELLOW)
        paint.color = Color.BLUE
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, RADIUS)
            }
        }
        clipToOutline = true
        animator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize((DEF_WIDTH).toInt(), widthMeasureSpec)
        val height = resolveSize((DEF_HEIGHT).toInt(), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private val color1 by lazy {
        LinearGradient(
            width * 1f,
            height / 2f,
            width * 1f,
            height * 1f,
            intArrayOf(Color.TRANSPARENT, Color.RED),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    private val color2 by lazy {
        LinearGradient(
            width /2f,
            0f
            ,width/2f
            ,height / 2f,
            intArrayOf(Color.GREEN,Color.TRANSPARENT),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    private var currentSpeed = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val rectF by lazy {
        val left = 0f + RADIUS /2
        val top = 0f + RADIUS /2
        val right = left + DEF_WIDTH - RADIUS
        val bottom = top + DEF_HEIGHT - RADIUS
        RectF(left, top, right, bottom)
    }

    private val path by lazy {
        Path().also {
            it.addRoundRect(rectF, RADIUS, RADIUS, Path.Direction.CCW)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.withSave {
            canvas.rotate(currentSpeed, width / 2f, height / 2f)
            val left1 = rectF.left + rectF.width() / 2f
            val right1 = rectF.right + rectF.width()
            val top1 = rectF.top + rectF.height() / 2f
            val bottom1 = rectF.bottom + rectF.height() / 2f

            paint.shader = color1
//        paint.color = Color.RED
//        KLog.logI("left1: $left1");
//        KLog.logI("top1: $top1");
//        KLog.logI("right1: $right1");
//        KLog.logI("bottom1: $bottom1");
//        KLog.logI("-right1: ${-right1}");
//        KLog.logI("-bottom1: ${-bottom1}")
            canvas.drawRect(rectF.left + rectF.width() /2f , rectF.top + rectF.height() / 2f, rectF.right + RADIUS * 2, rectF.bottom+ RADIUS *2 , paint)
            paint.shader = null

            paint.shader = color2
//        paint.color = Color.GREEN
            canvas.drawRect(rectF.left - RADIUS * 2, rectF.top - RADIUS *2, rectF.left + rectF.width() /2f, rectF.top + rectF.height() / 2f, paint)
            paint.shader = null
        }
        paint.color = Color.BLUE
            canvas.drawRoundRect(rectF, RADIUS, RADIUS, paint)
    }
}