package com.android.custview.learn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.custview.R;
import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class MultiTouchView1 extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap bitmap;
    private float downX;
    private float downY;
    private float offsetX;
    private float offsetY;
    private float originOffsetX;
    private float originOffsetY;

    private int trackingPointerId;

    private static final float IMAGE_WIDTH = CommonUtils.dp2Px(200);

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = CommonUtils.getAvatar(context, R.drawable.avatar, (int) IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                trackingPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originOffsetX = offsetX;
                originOffsetY = offsetY;
                break;

            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingPointerId);
                offsetX = originOffsetX + event.getX(index) - downX;
                offsetY = originOffsetY + event.getY(index) - downY;
                invalidate();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                trackingPointerId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                originOffsetX = offsetX;
                originOffsetY = offsetY;

                break;

            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                if (pointerId == trackingPointerId) {
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    trackingPointerId = event.getPointerId(newIndex);
                    downX = event.getX(newIndex);
                    downY = event.getY(newIndex);
                    originOffsetX = offsetX;
                    originOffsetY = offsetY;
                }
                break;


        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}
