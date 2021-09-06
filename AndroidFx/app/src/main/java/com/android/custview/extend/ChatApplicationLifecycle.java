package com.android.custview.extend;

import android.app.Application;

import com.android.zp.base.KLog;
import com.android.zp.base.module.AbsApplicationLifecycle;

public class ChatApplicationLifecycle extends AbsApplicationLifecycle {
    public ChatApplicationLifecycle() {
        super(ChatApplicationLifecycle.class.getCanonicalName());
    }

    @Override
    protected void onModuleCreate(Application application) {
        KLog.logI("ChatApplicationLifecycle onModuleCreate");
    }
}
