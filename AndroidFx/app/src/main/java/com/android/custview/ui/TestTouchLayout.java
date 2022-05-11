package com.android.custview.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.android.zp.base.KLog;

public class TestTouchLayout extends RelativeLayout {

    public TestTouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.logI("TestTouchLayout dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        KLog.logI("TestTouchLayout onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.logI("TestTouchLayout onTouchEvent");
        return super.onTouchEvent(event);
    }
}
