package com.android.custview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class VDHLayout extends LinearLayout {

    private ViewDragHelper mDragger;

    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();

    public VDHLayout(Context context) {
        this(context, null);
    }

    public VDHLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VDHLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return top;
            }

            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == mDragView || child == mAutoBackView;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                if (releasedChild == mAutoBackView) {
                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mDragger.captureChildView(mEdgeTrackerView, pointerId);
            }
        });
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }
}
