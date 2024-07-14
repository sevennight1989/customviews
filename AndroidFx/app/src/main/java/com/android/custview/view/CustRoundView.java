package com.android.custview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.android.zp.base.KLog;

import androidx.annotation.Nullable;

public class CustRoundView extends View {
    private static final String TAG = "CustRoundView";
    private Paint mPaint;
    private Path mPath;
    private float mCurrentX = 500;
    private float mCurrentY = 100;
    public CustRoundView(Context context) {
        this(context,null);
    }

    public CustRoundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustRoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, 500);
        mPath.lineTo(0, 100);
//        mPath.quadTo(0, 0, 100, 0);
        mPath.lineTo(400,0);
//        mPath.quadTo(500, 0, 500, 100);
        mPath.lineTo(500,500);
        mPath.close();
        canvas.drawPath(mPath, mPaint);

    }

    public void updateView(float x,float y){
        KLog.logI("updateView: " + x +" , " + y);
        mCurrentX  =x;
        mCurrentY = y;
        postInvalidate();
    }
}
