package com.android.custview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;

import java.util.Random;

public class RandomTextView extends View {

    private String mRandomText;

    private int mRandomTextColor;

    private int mRandomTextSize;

    private Paint mPaint = new Paint();

    private Rect bound = new Rect();

    public RandomTextView(Context context) {
        this(context, null);
    }

    public RandomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RandomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRandomText = "1234";
        mRandomTextColor = Color.parseColor("#03DAC5");
        mRandomTextSize = ConvertUtils.sp2px(20f);
        mPaint.setTextSize(mRandomTextSize);
        mPaint.getTextBounds(mRandomText, 0, mRandomText.length(), bound);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRandomText = getRandomText();
                postInvalidate();
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                width = widthSize + getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.AT_MOST:
                width = bound.width() + getPaddingLeft() + getPaddingRight();
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                height = heightSize + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.AT_MOST:
                height = bound.height() + getPaddingTop() + getPaddingBottom();
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(-getPaddingLeft(), -getPaddingTop(), getMeasuredWidth() + getPaddingRight(), getMeasuredHeight() + getPaddingBottom(), mPaint);

        mPaint.setColor(mRandomTextColor);
        canvas.drawText(mRandomText, getWidth() / 2 - bound.width() / 2, getHeight() / 2 + bound.height() / 2, mPaint);
    }

    private String getRandomText(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < mRandomText.length();i++){
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }
}
