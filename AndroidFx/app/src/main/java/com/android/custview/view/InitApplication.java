package com.android.custview.view;

import android.app.Application;

import com.com.android.custview.KLog;

public class InitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String logDir = AppManifestUtil.getAndroidManifestStringValueByName(this, AppManifestUtil.ManifestName.LogDir);
        KLog.logD("LogDir: " + logDir);
    }
}
