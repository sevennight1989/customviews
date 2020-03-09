package com.android.custview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class SpeedLimitView extends View {

    private Paint mPaint;

    public SpeedLimitView(Context context) {
        this(context,null);
    }

    public SpeedLimitView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpeedLimitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectFBg = new RectF();
        rectFBg.left = 0;
        rectFBg.right = 500;
        rectFBg.top = 0 ;
        rectFBg.bottom = 200;
        canvas.drawRoundRect(rectFBg,100,100,mPaint);

        RectF arc1 = new RectF();
        arc1.left = 30;
        arc1.right = 170;
        arc1.top = 30;
        arc1.bottom = 170;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(arc1,0,360,false,mPaint);

        RectF arc2 = new RectF();
        arc2.left = 20;
        arc2.right = 180;
        arc2.top = 20;
        arc2.bottom = 180;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(arc2,0,360,false,mPaint);

    }
}
