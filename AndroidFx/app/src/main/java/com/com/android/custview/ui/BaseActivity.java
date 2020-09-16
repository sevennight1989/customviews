package com.com.android.custview.ui;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (!showActionBar()) {
                actionBar.hide();
            }
        }
        initView();
        initData();
    }

    public boolean showActionBar() {
        return false;
    }

    public abstract @LayoutRes
    int getLayout();

    public abstract void initView();

    public abstract void initData();
}
