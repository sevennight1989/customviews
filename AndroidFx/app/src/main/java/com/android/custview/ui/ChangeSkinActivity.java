package com.android.custview.ui;

import android.content.res.Configuration;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

import com.android.custview.R;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;
import com.android.zp.base.utils.PreferencesUtils;

public class ChangeSkinActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.change_skin_layout;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    // 点击事件
    public void dayOrNight(View view) {
        int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        KLog.logE("uiMode: " + uiMode);
        switch (uiMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                PreferencesUtils.putBoolean(this, "isNight", true);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                PreferencesUtils.putBoolean(this, "isNight", false);
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}

