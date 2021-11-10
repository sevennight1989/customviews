package com.android.custview.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import com.android.custview.inf.UpdateTextListener
import com.blankj.utilcode.util.ConvertUtils
import kotlin.math.ceil
import kotlin.math.min

class HorizontalWaveProgressView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int
) : View(context, attrs, defStyleAttr) {


    private var wavePaint: Paint? = null
    private var wavePath: Path? = null
    private val waveLength: Float = 0f
    private val waveHeight: Float = 0f
    private var waveNumber: Int = 0
    private var waveDefaultWidth: Int = 0
    private var waveDefaultHeight: Int = 0
    private var waveActualSizeWidth: Int = 0
    private var waveActualSizeHeight: Int = 0
    private var currentPercent: Float = 0f
    private var currentProgress: Int = 0
    private var maxProgress: Int = 0
    private var waveProgressAnimal: WaveProgressAnimal? = null
    private var mProgressAnimator: ValueAnimator? = null
    private var mEndAnimator: ValueAnimator? = null
    private var moveDistance: Float = 0f
    private var backgroundPaint: Paint? = null
    private var borderPaint: Paint? = null
    private var circleBitmap: Bitmap? = null
    private var bitmapCanvas: Canvas? = null
    private val waveColor: Int = Color.GREEN
    private val backgroundColor: Int = Color.GRAY
    private var updateTextListener: UpdateTextListener? = null
    private var isShowSecondWave: Boolean = true
    private val secondWaveColor: Int = Color.YELLOW
    private val borderColor: Int = Color.BLUE
    private var secondWavePaint: Paint? = null
    private var secondWavePath: Path? = null
    private var dp1: Int = ConvertUtils.dp2px(1.0f)
    private var dp27: Int = ConvertUtils.dp2px(27.0f)


    init {
        init(context)
    }


    private fun init(context: Context) {
        waveDefaultWidth = ConvertUtils.dp2px(152f)
        waveDefaultHeight = ConvertUtils.dp2px(40f)
        wavePath = Path()
        wavePaint = Paint().apply {
            xfermode = (PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
            color = waveColor
            isAntiAlias = true
        }

        borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = borderColor
            isAntiAlias = true
            strokeWidth = dp1.toFloat()
            style = Paint.Style.STROKE
        }

        if (isShowSecondWave) {
            secondWavePath = Path()
            secondWavePaint = Paint().apply {
                color = secondWaveColor
                isAntiAlias = true
                xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
            }
        }

        currentPercent = currentProgress * 1f / maxProgress

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width: Int = measureSize(waveDefaultWidth, widthMeasureSpec)
        val height: Int = measureSize(waveDefaultHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
        waveActualSizeWidth = width
        waveActualSizeHeight = height

        waveNumber = ceil((waveActualSizeHeight / waveLength / 2).toDouble()).toInt()


    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var result: Int = defaultSize
        val specMode: Int = MeasureSpec.getMode(measureSpec)
        val specSize: Int = MeasureSpec.getSize(defaultSize)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = min(result, specSize)
        }
        return result
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        circleBitmap =
            Bitmap.createBitmap(waveActualSizeWidth, waveActualSizeHeight, Bitmap.Config.ARGB_8888)
        bitmapCanvas = Canvas(circleBitmap!!)
        val rectBg: RectF =
            RectF(0f, 0f, waveActualSizeWidth.toFloat(), waveActualSizeHeight.toFloat())
        bitmapCanvas!!.drawRoundRect(rectBg, dp27.toFloat(), dp27.toFloat(), backgroundPaint!!)
    }

    class WaveProgressAnimal : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)

        }
    }
}

