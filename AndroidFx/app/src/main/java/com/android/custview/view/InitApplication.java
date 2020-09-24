package com.android.custview.view;

import android.app.Application;

import com.android.custview.BuildConfig;
import com.com.android.custview.KLog;
import com.debug.Head;

public class InitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String logDir = AppManifestUtil.getAndroidManifestStringValueByName(this, AppManifestUtil.ManifestName.LogDir);
        KLog.logD("LogDir: " + logDir);
        KLog.logD("AppName : " + BuildConfig.appName);
        KLog.logD("MIN_HU_VERSION :"  + BuildConfig.MIN_HU_VERSION);
        KLog.logD(  "MAX_HU_VERSION: " + BuildConfig.MAX_HU_VERSION);
        KLog.logD("Head: " + Head.HEAD_NAME);
    }
}
