package com.android.custview.learn;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {

    public SquareImageView(@NonNull Context context) {
        this(context, null);
    }

    public SquareImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

/*    @Override
    public void layout(int l, int t, int r, int b) {
        int width = r-l;
        int height = b - t;
        int size = Math.max(width,height);
        super.layout(l, t, r + size, b+size);
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();
        int size = Math.max(measureWidth,measureHeight);
        setMeasuredDimension(size,size);//保存测得的尺寸


    }
}
