package com.android.custview.live;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.android.custview.R;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.FragmentStackManager;
import com.android.zp.base.KLog;
import com.blankj.utilcode.util.BarUtils;


public class LiveMainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_live_main;
    }

    @Override
    public void initView() {
        BarUtils.setStatusBarLightMode(this,true);
        FragmentStackManager.FragmentConfig fragmentConfig = new FragmentStackManager.FragmentConfig(getSupportFragmentManager(), R.id.fg_holder);
        FragmentStackManager.getInstance().setGlobalConfig(this, fragmentConfig);
        FragmentStackManager.getInstance().setCallBack(new FragmentStackManager.Callback() {
            @Override
            public void onFragmentStackNull() {
                KLog.logI("LiveMainActivity onFragmentStackNull");
                finish();
            }
        });
        FragmentStackManager.getInstance().add(this, LiveFragment01.class);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        KLog.logI("onBackPressed");
        FragmentStackManager.getInstance().finish(this);
    }
}
