package com.android.custview.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;

import com.android.custview.R;
import com.com.android.custview.KLog;

import java.util.Locale;

public class LanguageTestActivity extends BaseActivity {
    @Override
    public int getLayout() {
        return R.layout.activity_lauguage;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_en:
                switchEn();
                break;

            case R.id.switch_cn:
                switchCn();
                break;

            case R.id.toMain:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void switchEn() {
        switchLocate(Locale.ENGLISH);
    }

    private void switchCn() {
        switchLocate(Locale.SIMPLIFIED_CHINESE);
    }

    private void switchLocate(Locale locale) {
        KLog.logD("switchLocate:" + locale.getDisplayName());
        Resources resources = getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale;
        resources.updateConfiguration(config, dm);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(doAttachBaseContext(newBase));
    }

    public Context doAttachBaseContext(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, Locale.ENGLISH);
        } else {
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResources(Context context, Locale locale) {
        if (null == locale) {
            return context;
        }
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        KLog.logD("updateResources ");
        return context.createConfigurationContext(configuration);
    }
}
