package com.android.custview.ui;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.android.custview.R;
import com.android.zp.base.BaseActivity;

import java.lang.reflect.Method;

public class ProgressBarActivity extends BaseActivity {

    private ProgressBar mPb;

    @Override
    public int getLayout() {
        return R.layout.activity_progress_bar;
    }

    @Override
    public void initView() {
        mPb = findViewById(R.id.progressBar);
        mPb.setMax(100);
        mPb.setProgress(30);
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
                break;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mPb.setProgressDrawableTiled(ContextCompat.getDrawable(this, resId));
        } else {
            Drawable layerDrawable = mPb.getResources().getDrawable(resId);
            Drawable drawable = getMethod(mPb, new Object[]{layerDrawable, false});
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
