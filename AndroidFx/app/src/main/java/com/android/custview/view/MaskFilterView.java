package com.android.custview.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.android.zp.base.KLog;

import androidx.annotation.Nullable;

public class MaskFilterView extends View {

    private Paint lightPaint;
    //背景色
    private int color = Color.parseColor("#EC3E3E");
    private int centerX, centerY;
    /**
     * 发光范围
     */
    private int radioRadius = 70;
    /**
     * 圆的半径
     */
    private int circleRadius = 150;

    public MaskFilterView(Context context) {
        this(context, null);
    }

    public MaskFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        lightPaint = new Paint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        lightPaint.setColor(color);
        lightPaint.setMaskFilter(new BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.INNER));
    }

    public MaskFilterView setRadioRadius(int radioRadius) {
        this.radioRadius = radioRadius;
        return this;
    }

    public MaskFilterView setColor(int color) {
        this.color = color;
        return this;
    }

    public MaskFilterView setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        return this;
    }

    public void refreshUI() {
        invalidate();
    }

    public MaskFilterView setBlurType(int blurType) {
        switch (blurType) {
            case 0:
                lightPaint.setMaskFilter(new BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.INNER));
                break;
            case 1:
                lightPaint.setMaskFilter(new BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.NORMAL));
                break;
            case 2:
                lightPaint.setMaskFilter(new BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.SOLID));
                break;
            case 3:
                lightPaint.setMaskFilter(new BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.OUTER));
                break;
        }
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getLeft() + getMeasuredWidth() / 2;
        centerY = getTop() + getMeasuredHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        KLog.logI("centerX: " + centerX + " centerY :" + centerY + " circleRadius: " + circleRadius);
        canvas.drawCircle(centerX, centerY, circleRadius, lightPaint);
    }
}

