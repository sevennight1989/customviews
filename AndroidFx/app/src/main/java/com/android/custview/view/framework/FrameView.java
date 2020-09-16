package com.android.custview.view.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 卡片框架的父控件，主要用于处理子控件添加删除的动画等功能
 */
public class FrameView extends FrameLayout {

    public FrameView(@NonNull Context context) {
        this(context,null);
    }

    public FrameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FrameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
