package com.android.custview.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class DashboardView extends View {

    private static final float ANGLE = 120;
    private static final float RADIUS = CommonUtils.dp2Px(150);
    private static final float LENGTH = CommonUtils.dp2Px(100);;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path dash = new Path();
    private PathDashPathEffect pathDashPathEffect;
    private RectF arcRectF;

    public DashboardView(Context context) {
        this(context, null);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.RED);
        {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(CommonUtils.dp2Px(2));
            dash.addRect(0, 0, CommonUtils.dp2Px(2), CommonUtils.dp2Px(10), Path.Direction.CW);

            Path arc = new Path();
            initArc();
            arc.addArc(arcRectF, 90 + ANGLE / 2, 360 - ANGLE);
            PathMeasure pathMeasure = new PathMeasure(arc, false);
            float len = pathMeasure.getLength();
            pathDashPathEffect = new PathDashPathEffect(dash, (len - CommonUtils.dp2Px(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private void initArc() {
        arcRectF = new RectF(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initArc();
        canvas.drawArc(arcRectF,
                90 + ANGLE / 2, 360 - ANGLE, false, paint);
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawArc(arcRectF,
                90 + ANGLE / 2, 360 - ANGLE, false, paint);
        paint.setPathEffect(null);
        canvas.drawLine(getWidth() / 2, getHeight() / 2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + getWidth()/2,
                (float) Math.sin(Math.toRadians(getAngleFromMark(5))) * LENGTH + getHeight()/2,
                paint);
    }

    private int getAngleFromMark(int mark) {
        return (int) (90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark);
    }
}
