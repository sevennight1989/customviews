package com.android.custview.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.android.zp.base.KLog;

import androidx.annotation.Nullable;

public class Learn01View extends View {
    private Paint mPaint;
    private Path mPath = new Path();
    private PathMeasure pathMeasure;

    public Learn01View(Context context) {
        this(context, null);
    }

    public Learn01View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Learn01View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.reset();
        mPath.addRect(getWidth() / 2 - 150, getHeight() / 2 - 300, getWidth() / 2 + 150, getHeight() / 2, Path.Direction.CCW);
        mPath.addCircle(getWidth()/2,getHeight()/2,150, Path.Direction.CW);
        mPath.addCircle(getWidth()/2,getHeight()/2,400, Path.Direction.CW);
        pathMeasure = new PathMeasure(mPath,false);
        float len = pathMeasure.getLength();
        KLog.logI("pathMeasure : " + len);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(mPath, mPaint);
    }
}
