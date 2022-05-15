package com.android.custview.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class MultiTouchView3 extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private SparseArray<Path> paths = new SparseArray<>();

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(CommonUtils.dp2Px(4));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Path path = new Path();
                int actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex));
                paths.append(pointerId,path);
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    pointerId = event.getPointerId(i);
                    path = paths.get(pointerId);
                    path.lineTo(event.getX(i), event.getY(i));
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                pointerId = event.getPointerId(event.getActionIndex());
                paths.remove(pointerId);
                invalidate();
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.valueAt(i), paint);
        }
    }
}
