package com.android.custview.learn;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


import androidx.annotation.Nullable;

public class OneHundredView extends View {
    public OneHundredView(Context context) {
        this(context,null);
    }

    public OneHundredView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OneHundredView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(100,100);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right , bottom );
    }
}
