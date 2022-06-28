package com.android.custview.ui;

import android.view.View;

import com.android.custview.R;
import com.android.custview.learn.TouchView;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;
import com.blankj.utilcode.util.ToastUtils;

public class Learn04Activity extends BaseActivity {
    private TouchView touchView;
    private static final int MODE_SHIFT = 30;
    // 声明位移量
    private static final int MODE_MASK = 0x3 << MODE_SHIFT;
// 后期截取SpecMode或SpecSize时使用的变量
// 3对应的二进制是11，左移30位后，int值的前2位就都是1，后30位为0

    public static final int UNSPECIFIED = 0 << MODE_SHIFT;
    public static final int EXACTLY = 1 << MODE_SHIFT;

    public static final int AT_MOST = 2 << MODE_SHIFT;

    @Override
    public int getLayout() {
        return R.layout.activity_learn04;
    }

    @Override
    public void initView() {
//        touchView = findViewById(R.id.touchView);
//        touchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showShort("ClickMe");
//            }
//        });
    }

    @Override
    public void initData() {
        int spec = makeMeasureSpec(100, AT_MOST);
        int mode = getMode(spec);
        int size = getSize(spec);
        KLog.logI("size = " + size);
        switch (mode){
            case UNSPECIFIED:
                KLog.logI("UNSPECIFIED");
                break;
            case EXACTLY:
                KLog.logI("EXACTLY");
                break;
            case AT_MOST:
                KLog.logI("AT_MOST");
                break;
        }
    }

    // 等价于@IntDef(value={...})。一种枚举类注释
    public static int getMode(int measureSpec) {
        return (measureSpec & MODE_MASK);
        // 让低30位的值变为0，只保留高2位的值
    }

    public static int getSize(int measureSpec) {
        return (measureSpec & ~MODE_MASK);
        // 非运算直接让MASK值变成int值高2位为0，低30位为1
        // 进行与运算，直接将高2位的值变为0

    }

    public static int makeMeasureSpec(int size, int mode) {
        return (size & ~MODE_MASK) | (mode & MODE_MASK);
    }
}