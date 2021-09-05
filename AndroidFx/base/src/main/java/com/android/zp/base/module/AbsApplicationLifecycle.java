package com.android.zp.base.module;

import android.app.Application;

public abstract class AbsApplicationLifecycle {
    private final String moduleName;

    public AbsApplicationLifecycle(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    protected abstract void onModuleCreate(Application application);
}
