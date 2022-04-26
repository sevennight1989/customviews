package com.android.custview.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;

import com.android.custview.R;
import com.android.custview.constant.PJConstant;
import com.android.custview.impl.AnalyticsDelegateImpl;
import com.android.custview.impl.LogoutDelegateImpl;
import com.android.custview.inf.AnalyticsDelegate;
import com.android.custview.inf.LogoutDelegate;
import com.android.custview.view.MaskFilterView;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.utils.RoundRect;

import java.lang.reflect.Method;

public class ProgressBarActivity extends BaseActivity  {

    private ProgressBar mPb;
    private MaskFilterView maskFilterView1;
    private MaskFilterView maskFilterView2;
    private MaskFilterView maskFilterView3;
    private MaskFilterView maskFilterView4;
    private ImageView image2;
    private AnalyticsDelegate analyticsDelegate;
    private LogoutDelegate logoutDelegate;

    @Override
    public int getLayout() {
        return R.layout.activity_progress_bar;
    }

    @Override
    public void initView() {
        mPb = findViewById(R.id.progressBar);
        mPb.setMax(100);
        mPb.setProgress(30);
        maskFilterView1 = findViewById(R.id.mast_01);
        maskFilterView2 = findViewById(R.id.mast_02);
        maskFilterView3 = findViewById(R.id.mast_03);
        maskFilterView4 = findViewById(R.id.mast_04);
        image2 = findViewById(R.id.image2);
        maskFilterView1.setBlurType(0);
        maskFilterView2.setBlurType(1);
        maskFilterView4.setBlurType(3);
        maskFilterView3.setCircleRadius(130).setRadioRadius(30).setBlurType(2).refreshUI();
        RoundRect roundRect2 = new RoundRect(
                200, 200, 100f);
        Bitmap bitmap  = roundRect2.toRoundRect(this, R.mipmap.touxiang);
        image2.setImageBitmap(bitmap);
        analyticsDelegate = new AnalyticsDelegateImpl();
        analyticsDelegate.registerAnalytics(getLifecycle());
        logoutDelegate = new LogoutDelegateImpl();
        logoutDelegate.registerLogout(this);
    }

    @Override
    public void initData() {

    }

    public void onClick(View view) {
        int resId = 0;
        switch (view.getId()) {
            case R.id.switchColor1:
                resId = R.drawable.progress;
                break;

            case R.id.switchColor2:
                resId = R.drawable.progress2;
                Intent intent = new Intent(PJConstant.LOGOUT);
                sendBroadcast(intent);
                break;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mPb.setProgressDrawableTiled(ContextCompat.getDrawable(this, resId));
        } else {
            Drawable layerDrawable = mPb.getResources().getDrawable(resId);
            Drawable drawable = getMethod(mPb, new Object[] {layerDrawable, false});
            mPb.setProgressDrawable(drawable);
        }
    }

    private Drawable getMethod(Object object, Object[] paras) {
        Drawable newDrawable = null;
        try {
            Class<?>[] c = new Class[2];
            c[0] = Drawable.class;
            c[1] = boolean.class;
            String name = "tileify";
            @SuppressLint("PrivateApi")
            Method method = ProgressBar.class.getDeclaredMethod(name, c);
            method.setAccessible(true);
            newDrawable = (Drawable) method.invoke(object, paras);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDrawable;
    }
}
