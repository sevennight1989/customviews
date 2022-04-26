package com.android.custview.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.custview.constant.PJConstant;
import com.android.custview.inf.LogoutDelegate;
import com.android.zp.base.KLog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class LogoutDelegateImpl implements LogoutDelegate, DefaultLifecycleObserver {

    private AppCompatActivity activity;
    private BroadcastReceiver logoutReceiver;

    @Override
    public void registerLogout(AppCompatActivity activity) {
        KLog.logI("LogoutDelegateImpl registerLogout");
        this.activity = activity;
        this.activity.getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        KLog.logI("LogoutDelegateImpl onCreate: " + activity.getClass().getSimpleName());
        logoutReceiver = new LogoutReceiver();
        activity.registerReceiver(logoutReceiver, new IntentFilter(PJConstant.LOGOUT));
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        KLog.logI("LogoutDelegateImpl onDestroy");
        activity.unregisterReceiver(logoutReceiver);
    }

    private class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            KLog.logI("LogoutReceiver onReceive");
            activity.finish();
        }
    }
}
