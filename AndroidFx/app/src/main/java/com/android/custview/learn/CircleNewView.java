package com.android.custview.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class CircleNewView extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final int RADIUS = (int) CommonUtils.dp2Px(80);
    private static final int PADDING = (int) CommonUtils.dp2Px(30);

    public CircleNewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (PADDING + RADIUS) * 2;
        int height = (PADDING + RADIUS) * 2;

//        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        width = resolveSizeAndState(width, widthMeasureSpec, 0);
        height = resolveSizeAndState(height, heightMeasureSpec, 0);

//        switch (specWidthMode) {
//            case MeasureSpec.EXACTLY:
//                width = specWidthSize;
//                break;
//            case MeasureSpec.AT_MOST:
//                if (width > specWidthSize) {
//                    width = specWidthSize;
//                }
//                break;
//        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint);
    }
}
