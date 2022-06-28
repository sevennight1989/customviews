package com.android.custview.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class PointView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
        invalidate();
    }

    private Point point = new Point(0,0);

    public PointView(Context context) {
        this(context,null);
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        {
            paint.setColor(Color.RED);
            paint.setStrokeWidth(CommonUtils.dp2Px(5));
            paint.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(point.x,point.y,paint);
    }
}
