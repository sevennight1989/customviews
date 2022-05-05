package com.android.custview.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class SportsView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RADIUS = CommonUtils.dp2Px(100);
    private static final float S_WIDTH = CommonUtils.dp2Px(10);
    private Rect rect = new Rect();
    private static final String TEXT = "abABJ";
    private Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private boolean usCommon = true;//使用最通用的文字基线

    public SportsView(Context context) {
        this(context, null);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        {
            paint.setTextSize(CommonUtils.dp2Px(50));
            paint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "GOUDOSB.TTF"));
            paint.setTextAlign(Paint.Align.CENTER);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(S_WIDTH);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, -90, 225, false, paint);
        paint.setStyle(Paint.Style.FILL);

        if (usCommon) {
            paint.getFontMetrics(fontMetrics);
            paint.setTextAlign(Paint.Align.CENTER);
            float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
            canvas.drawText(TEXT, getWidth() / 2, getHeight() / 2 - offset, paint);

            paint.setTextSize(CommonUtils.dp2Px(100));
            paint.setTextAlign(Paint.Align.LEFT);
            paint.getTextBounds(TEXT, 0, TEXT.length(), rect);
            canvas.drawText(TEXT, -rect.left, 200, paint);


            paint.setTextSize(CommonUtils.dp2Px(15));
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(TEXT, 0, 200 + paint.getFontSpacing(), paint);

        } else {
            paint.getTextBounds(TEXT, 0, TEXT.length(), rect);
            int offset = (rect.top + rect.bottom) / 2;
            canvas.drawText(TEXT, getWidth() / 2, getHeight() / 2 - offset, paint);
        }
    }
}
