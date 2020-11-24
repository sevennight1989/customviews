package com.android.custview.ui;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;

public abstract class BaseActivity extends AppCompatActivity {
    private LifecycleRegistry lifecycleRegistry;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        lifecycleRegistry =  new LifecycleRegistry( this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (!showActionBar()) {
                actionBar.hide();
            }
        }
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    public boolean showActionBar() {
        return false;
    }

    public abstract @LayoutRes
    int getLayout();

    public abstract void initView();

    public abstract void initData();
}
