package com.android.custview.learn;

import android.annotation.SuppressLint;
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

public class MultiTouchView2 extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Bitmap bitmap;
    private float downX;
    private float downY;
    private float offsetX;
    private float offsetY;
    private float originOffsetX;
    private float originOffsetY;


    private static final float IMAGE_WIDTH = CommonUtils.dp2Px(200);

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = CommonUtils.getAvatar(context, R.drawable.avatar, (int) IMAGE_WIDTH);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float sumX = 0;
        float sumY = 0;
        int pointerCount = event.getPointerCount();
        boolean isPointerUp = event.getActionMasked() == MotionEvent.ACTION_POINTER_UP;
        for (int i = 0; i < pointerCount; i++) {
            if(!( isPointerUp && i == event.getActionIndex())){
                sumX += event.getX(i);
                sumY += event.getY(i);
            }
        }
        if (isPointerUp) {
            pointerCount -= 1;
        }
        float focusX = sumX / pointerCount;
        float focusY = sumY / pointerCount;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                downX = focusX;
                downY = focusY;
                originOffsetX = offsetX;
                originOffsetY = offsetY;
                break;

            case MotionEvent.ACTION_MOVE:
                offsetX = originOffsetX + focusX - downX;
                offsetY = originOffsetY + focusY - downY;
                invalidate();
                break;


        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}
