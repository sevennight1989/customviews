package com.android.custview.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Configuration;

import com.android.custview.BuildConfig;
import com.android.custview.extend.ChatApplicationLifecycle;
import com.android.custview.impl.ChatServiceImpl;
import com.android.custview.inf.ChatService;
import com.android.custview.utils.ConfigUtils;
import com.android.custview.utils.UniException;
import com.android.zp.base.BaseApplicaion;
import com.android.zp.base.KLog;
import com.android.zp.base.module.ApplicationLifecycleMgr;
import com.android.zp.base.module.ModuleServiceMgr;
import com.debug.Head;
import com.tencent.mmkv.MMKV;

import java.util.List;
import dagger.hilt.android.HiltAndroidApp;
@HiltAndroidApp
public class InitApplication extends Application implements Configuration.Provider {
    private static Context  mContext;
    private boolean enableBlackWriteMode = false;//开启黑白模式

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        if (!isMainProcess(this)) {
            return;
        }
//        UniException.getInstance().init(this);
        String rootDir = MMKV.initialize(this);
        BaseApplicaion.getInstance().onCreate(mContext);
        KLog.logI("rootDir: " + rootDir);
        ConfigUtils.Companion.getInstance(mContext).initDemoConfigs();
        String logDir = AppManifestUtil.getAndroidManifestStringValueByName(this, AppManifestUtil.ManifestName.LogDir);
        KLog.logI("LogDir: " + logDir);
        KLog.logI("AppName : " + BuildConfig.appName);
        KLog.logI("MIN_HU_VERSION :" + BuildConfig.MIN_HU_VERSION);
        KLog.logI("MAX_HU_VERSION: " + BuildConfig.MAX_HU_VERSION);
        KLog.logI("Head: " + Head.HEAD_NAME);
//        KLog.logI("ProcessName: " + getProcessName());
        KLog.logI("ProcessName: " + getProcessName(android.os.Process.myPid()));

        ApplicationLifecycleMgr.getInstance()
                .registerLifecycle(new ChatApplicationLifecycle())
                .notifyOnCreate(this);

        ModuleServiceMgr.getInstance()
                .registerService(ChatService.class,getApplicationContext(),new ChatServiceImpl());
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                if(enableBlackWriteMode){
                    View decorView = activity.getWindow().getDecorView();
                    decorView.setLayerType(View.LAYER_TYPE_HARDWARE,paint);
                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });

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

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        int logLevel = Log.VERBOSE;
        if (BuildConfig.DEBUG) {
            logLevel = Log.DEBUG;
        } else {
            logLevel = Log.ERROR;
        }
        return new Configuration.Builder().setMinimumLoggingLevel(logLevel).build();
    }

    private boolean isMainProcess(Context context) {
        String pkgName = context.getPackageName();
        int pid = Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processesList = am.getRunningAppProcesses();
        if (null != processesList) {
            for (ActivityManager.RunningAppProcessInfo info : processesList) {
                if (null != info && info.pid == pid) {
                    return pkgName.equals(info.processName);
                }
            }
        }

        return false;
    }
}
