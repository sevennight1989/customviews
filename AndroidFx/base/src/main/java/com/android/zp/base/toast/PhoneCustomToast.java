package com.android.zp.base.toast;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zp.base.R;


public class PhoneCustomToast {
    private static final int DELAY_REMOVE_VIEW = 3000;
    private Context mContext;
    private View mConvertView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    private TextView mPhoneCustomExplain;
    public boolean isShow;

    public PhoneCustomToast(Context context) {
        mContext = context.getApplicationContext();
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 96,
                2024,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        //| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                        | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP;
        params.windowAnimations = R.style.toast_dialog_anim;
    }

    public PhoneCustomToast onCreate() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mConvertView = inflater.inflate(R.layout.default_toast, null);
        mPhoneCustomExplain = (TextView) mConvertView.findViewById(R.id.toast_text);
        return this;
    }

    public PhoneCustomToast setText(String contentText) {
        mPhoneCustomExplain.setText(contentText);
        return this;
    }

    public void show() {
        try {
            mWindowManager.addView(mConvertView, params);
            isShow = true;
        } catch (Exception e) {
            if (e != null && e.getMessage() != null) {
                Log.i("PhoneCustomToast", e.getMessage());
            }
        }
    }

    public void close() {
        try {
            mWindowManager.removeViewImmediate(mConvertView);
            isShow = false;
        } catch (Exception e) {
            if (e != null && e.getMessage() != null) {
                Log.i("PhoneCustomToast", e.getMessage());
            }
        }
    }

    private Runnable mDismissRunnable = new Runnable() {
        @Override
        public void run() {
            close();
        }
    };
}
