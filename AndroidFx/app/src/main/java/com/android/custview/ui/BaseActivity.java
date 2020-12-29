package com.android.custview.ui;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;

import com.android.custview.annotation.QuitReset;
import com.android.custview.utils.KLog;
import com.blankj.utilcode.util.SPUtils;

import java.lang.reflect.Field;

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

    @Override
    protected void onDestroy() {
        Class clazz = getClass();
        String className = clazz.getSimpleName();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (field.isAnnotationPresent(QuitReset.class)) {
                try {
                    QuitReset related = field.getAnnotation(QuitReset.class);
                    Object currentValue = field.get(this);
                    Class<?> filedType = field.getType();
                    KLog.logI("filedType: " + filedType);
                    switch (filedType.toString()) {
                        case "boolean":
                            boolean value = related.value();
                            SPUtils.getInstance().put(fieldName, value);
                            KLog.logI(className + " : " + fieldName + " value == " + value);
                            break;
                        case "int":
                            int state = related.state();
                            SPUtils.getInstance().put(fieldName, state);
                            KLog.logI(className + " : " + fieldName + " state == " + state);
                            break;
                    }
                    KLog.logI(className + " : " + fieldName + " currentValue == " + currentValue.toString());
                    //这里可以修改field的值，当前场景不需要修改
//                    field.setAccessible(true);
//                    field.set(this, value);
                } catch (Exception e) {
                    KLog.logE(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        super.onDestroy();

    }
}
