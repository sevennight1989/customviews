package com.android.zp.base.module;

import android.content.Context;

public interface ModuleService {
    /**
     * 初始化调用
     *
     * @param context application 上下文
     */
    void onInit(Context context);
}
