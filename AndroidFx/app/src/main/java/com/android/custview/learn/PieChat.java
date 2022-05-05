package com.android.custview.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class PieChat extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF bounds = new RectF();
    private static final float LENGTH = CommonUtils.dp2Px(20);;
    private static final int RADIUS = (int) CommonUtils.dp2Px(150);
    private static final int PULL_OUT_INDEX = 1;

    private static final int []angels = {60,100,120,80};
    private static final String []colors = {"#2979FF","#C2185B","#009688","#FF8F00"};
    public PieChat(Context context) {
        this(context, null);
    }

    public PieChat(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChat(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngle = 0;
        for (int i = 0; i < angels.length; i++) {
            paint.setColor(Color.parseColor(colors[i]));
            canvas.save();
            if (i == PULL_OUT_INDEX) {
                canvas.translate((float) Math.cos(Math.toRadians(currentAngle + angels[i] / 2)) * LENGTH ,
                        (float) Math.sin(Math.toRadians(currentAngle + angels[i] / 2)) * LENGTH );
            }
            canvas.drawArc(bounds, currentAngle, angels[i], true, paint);
            canvas.restore();
            currentAngle += angels[i];
        }

    }
}
