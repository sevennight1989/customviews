package com.android.custview.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.zp.base.KLog;

import androidx.annotation.Nullable;

public class TestTouchView extends View {

    public TestTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.logI("TestTouchView dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.logI("TestTouchView onTouchEvent");
        return super.onTouchEvent(event);
    }
}
