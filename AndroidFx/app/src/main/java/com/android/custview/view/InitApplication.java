package com.android.custview.view;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.android.custview.BuildConfig;
import com.com.android.custview.KLog;
import com.debug.Head;

import java.util.List;

public class InitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String logDir = AppManifestUtil.getAndroidManifestStringValueByName(this, AppManifestUtil.ManifestName.LogDir);
        KLog.logD("LogDir: " + logDir);
        KLog.logD("AppName : " + BuildConfig.appName);
        KLog.logD("MIN_HU_VERSION :" + BuildConfig.MIN_HU_VERSION);
        KLog.logD("MAX_HU_VERSION: " + BuildConfig.MAX_HU_VERSION);
        KLog.logD("Head: " + Head.HEAD_NAME);
        KLog.logD("ProcessName: " + getProcessName());
        KLog.logD("ProcessName: " + getProcessName(android.os.Process.myPid()));
    }

    /**
     * Only get current app's processInfo, use system api  getProcessName() instead.
     * @param pid
     * @return
     */
    @Deprecated
    public String getProcessName(int pid) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList =
                am.getRunningAppProcesses();
        if (processInfoList == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            KLog.logD("RunningAppProcessInfo : " + processInfo.pid + "  " + processInfo.processName);
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return null;
    }
}
