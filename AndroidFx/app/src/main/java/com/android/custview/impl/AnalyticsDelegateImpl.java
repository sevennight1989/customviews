package com.android.custview.impl;

import com.android.custview.inf.AnalyticsDelegate;
import com.android.zp.base.KLog;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class AnalyticsDelegateImpl implements AnalyticsDelegate , DefaultLifecycleObserver {
    @Override
    public void registerAnalytics(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        KLog.logI("AnalyticsDelegateImpl onCreate");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        KLog.logI("AnalyticsDelegateImpl onStart");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        KLog.logI("AnalyticsDelegateImpl onResume");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        KLog.logI("AnalyticsDelegateImpl onPause");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        KLog.logI("AnalyticsDelegateImpl onStop");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        KLog.logI("AnalyticsDelegateImpl onDestroy");
    }
}
