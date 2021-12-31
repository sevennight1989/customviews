package com.android.custview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;


public class LeanTextView extends androidx.appcompat.widget.AppCompatTextView {

    public int getmDegrees() {
        return mDegrees;
    }

    public void setmDegrees(int mDegrees) {
        this.mDegrees = mDegrees;
        invalidate();
    }

    private int mDegrees = 45;

    public LeanTextView(Context context) {
        super(context, null);
    }

    public LeanTextView(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.textViewStyle);
        this.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        canvas.rotate(-mDegrees, this.getWidth() / 2f, this.getHeight() / 2f);
        super.onDraw(canvas);
        canvas.restore();
    }
}