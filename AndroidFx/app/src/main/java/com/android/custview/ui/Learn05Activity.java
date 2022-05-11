package com.android.custview.ui;



import android.view.MotionEvent;

import com.android.custview.R;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;

public class Learn05Activity extends BaseActivity {


    @Override
    public int getLayout() {
        return R.layout.activity_learn05;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        KLog.logI("Learn05Activity dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        KLog.logI("Learn05Activity onTouchEvent");
        return true;
    }
}