package com.android.custview.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.android.custview.R;

public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {
    float width, height;

    // 圆角半径
    private float mRoundRage = 12;

    // 圆角类型：0=四周圆角；1=下边圆角；2=上边圆角；3=左边圆角；4=右边圆角
    // 8.1.2017 jackli 当前支持0,1,2
    private int mRoundType = 0;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mRoundRage = type.getFloat(R.styleable.RoundImageView_RoundRage, 12);
        mRoundType = type.getInt(R.styleable.RoundImageView_RoundType, 0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width > mRoundRage && height > mRoundRage) {
            Path path = new Path();
            doclipPath(canvas, path);
        }
        super.onDraw(canvas);
    }

    private void doclipPath(Canvas canvas, Path path) {
        switch (mRoundType) {
            case 0:
                path.moveTo(mRoundRage, 0);
                path.lineTo(width - mRoundRage, 0);
                path.quadTo(width, 0, width, mRoundRage);
                path.lineTo(width, height - mRoundRage);
                path.quadTo(width, height, width - mRoundRage, height);
                path.lineTo(mRoundRage, height);
                path.quadTo(0, height, 0, height - mRoundRage);
                path.lineTo(0, mRoundRage);
                path.quadTo(0, 0, mRoundRage, 0);
                break;
            case 1:
                path.moveTo(mRoundRage, 0);
                path.lineTo(width, 0);
                path.lineTo(width, height - mRoundRage);
                path.quadTo(width, height, width - mRoundRage, height);
                path.lineTo(mRoundRage, height);
                path.quadTo(0, height, 0, height - mRoundRage);
                path.lineTo(0, 0);
                break;
            case 2:
                path.moveTo(mRoundRage, 0);
                path.lineTo(width - mRoundRage, 0);
                path.quadTo(width, 0, width, mRoundRage);
                path.lineTo(width, height);
                path.lineTo(0, height);
                path.lineTo(0, mRoundRage);
                path.quadTo(0, 0, mRoundRage, 0);
                break;
            case 3:
                break;
            case 4:
                break;
        }
        canvas.clipPath(path);
    }
}