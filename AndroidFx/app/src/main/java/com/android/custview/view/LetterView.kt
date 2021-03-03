package com.android.custview.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.android.custview.R
import com.blankj.utilcode.util.ConvertUtils

interface OnLetterChangeListener {
    fun onLetterListener(touchIndex: String)
    fun onLetterDismissListener()
}

class LetterView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    var enableIndicator: Boolean? = true

    var mLetterPaint: Paint? = null

    var mLetterIndicatorPaint: Paint? = null

    var mWidth: Int = 0

    var mOnLetterChangeListener: OnLetterChangeListener? = null

    private var letters: Array<String> = arrayOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#")

    var mItemHeight: Int = 0

    var mTouchIndex: Int = -1

    var isTouch: Boolean = false

    init {
        val array = context?.obtainStyledAttributes(attrs, R.styleable.LetterView)
        val latterTextColor = array?.getColor(R.styleable.LetterView_letterTextColor, Color.RED)
        val latterTextBackgroundColor = array?.getColor(R.styleable.LetterView_letterTextBackgroundColor, Color.WHITE)
        val letterIndicatorColor = array?.getColor(R.styleable.LetterView_letterIndicatorColor, Color.parseColor("#333333"))
        val letterTextSize = array?.getDimension(R.styleable.LetterView_letterTextSize, 12f)
        enableIndicator = array?.getBoolean(R.styleable.LetterView_letterEnableIndicator, true)

        array?.recycle()
        mLetterPaint = Paint()
        mLetterPaint?.textSize = letterTextSize!!
        mLetterPaint?.color = latterTextColor!!
        mLetterPaint?.isAntiAlias = true

        mLetterIndicatorPaint = Paint()
        mLetterIndicatorPaint?.style = Paint.Style.FILL
        mLetterIndicatorPaint?.color = letterIndicatorColor!!
        mLetterIndicatorPaint?.isAntiAlias = true
        setBackgroundColor(latterTextBackgroundColor!!)
    }


    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val mRect = Rect()
        mLetterPaint?.getTextBounds("A", 0, 1, mRect)
        mWidth = mRect.width() + ConvertUtils.dp2px(12f)
        val mHeight = (mRect.height() + ConvertUtils.dp2px(5f)) * letters!!.size
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT && layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight)
        } else if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize)
        } else if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight)
        }
        mWidth = measuredWidth
        val averageItemHeight = measuredHeight / 28
        val mOffset = averageItemHeight / 30
        mItemHeight = averageItemHeight + mOffset
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rect = Rect()
        mLetterPaint?.getTextBounds("A", 0, 1, rect)
        val letterWidth = rect.width()
        val letterHeight = rect.height()
        if (enableIndicator == true) {
            for (i in 1 until letters.size + 1) {
                if (mTouchIndex == i) {
                    canvas?.drawCircle(0.5f * mWidth, i * mItemHeight - 0.5f * mItemHeight, 0.5f * mItemHeight, mLetterIndicatorPaint!!)
                }
            }
        }
        for (i in 1 until letters.size + 1) {
            canvas?.drawText(letters[i - 1], ((mWidth - letterWidth) / 2).toFloat(), mItemHeight * i - 0.5f * mItemHeight + letterHeight / 2, mLetterPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                isTouch = true
                val y = event.y
                val index = (y / mItemHeight).toInt()
                if (index != mTouchIndex && index < 28 && index > 0) {
                    mTouchIndex = index
                }
                if (mTouchIndex > 0) {
                    mOnLetterChangeListener?.onLetterListener(letters[mTouchIndex - 1])
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                isTouch = false
                if (mTouchIndex > 0) {
                    mOnLetterChangeListener?.onLetterDismissListener()
                }
            }
        }
        return true
    }

    public fun setCurrentIndex(word: String) {
        if (!isTouch) {
            for (i in letters.indices) {
                if (TextUtils.equals(letters[i], word)) {
                    mTouchIndex = i + 1
                    invalidate()
                    return
                }
            }
        }
    }
}