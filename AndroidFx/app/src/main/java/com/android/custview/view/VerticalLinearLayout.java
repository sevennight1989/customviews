package com.android.custview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

public class VerticalLinearLayout extends ViewGroup {

    private int mScreenHeight;

    private int mScrollStart;

    private int mScrollEnd;

    private int mLastY;

    private Scroller mScroller;

    private boolean isSrolling;

    private VelocityTracker mVelocityTracker;

    private int mCurrentPage = 0;

    public interface OnPageChangeListener {

        void onPageChange(int currentPage);
    }

    private OnPageChangeListener mOnPageChangeListener;

    public void setOnPageChangeListener(OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }

    public VerticalLinearLayout(Context context) {
        this(context, null);
    }

    public VerticalLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
