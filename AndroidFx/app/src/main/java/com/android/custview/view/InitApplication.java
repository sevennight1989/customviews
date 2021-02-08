package com.android.custview.view;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;


import com.android.custview.BuildConfig;
import com.android.zp.base.BaseApplicaion;
import com.android.zp.base.KLog;
import com.debug.Head;

import java.util.List;

public class InitApplication extends Application {
    private static Context  mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        BaseApplicaion.getInstance().onCreate(mContext);
        String logDir = AppManifestUtil.getAndroidManifestStringValueByName(this, AppManifestUtil.ManifestName.LogDir);
        KLog.logI("LogDir: " + logDir);
        KLog.logI("AppName : " + BuildConfig.appName);
        KLog.logI("MIN_HU_VERSION :" + BuildConfig.MIN_HU_VERSION);
        KLog.logI("MAX_HU_VERSION: " + BuildConfig.MAX_HU_VERSION);
        KLog.logI("Head: " + Head.HEAD_NAME);
//        KLog.logI("ProcessName: " + getProcessName());
        KLog.logI("ProcessName: " + getProcessName(android.os.Process.myPid()));
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
            KLog.logI("RunningAppProcessInfo : " + processInfo.pid + "  " + processInfo.processName);
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return null;
    }

    public static Context getContext(){
        return mContext;
    }

}
