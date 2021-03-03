package com.android.custview.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.android.custview.R;


public class IntercomTimeWindowService extends Service {
    private static final String TAG = "IntercomTimeWindowService";
    private WindowManager wm;
    private WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    private View rocket;
    private int width_phone, height_phone;
    private int WINDOWLEFT = 111;
    private int WINDOWRIGHT = 112;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        width_phone = display.getWidth(); //获取屏幕的宽度
        height_phone = display.getHeight();//获取屏幕的高度
        // params 默认情交是居中对齐的，屏幕中心点是0,0点
        params.gravity = Gravity.LEFT + Gravity.TOP; // 改为左上角对齐
        params.x = width_phone;
        params.y = height_phone/3*2;
        params.height = 300;
        params.width = 300;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;

        // 高优先级的窗体，可以显示在锁屏页面之上,但要添加权限
//        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        params.setTitle("Itcast rocket");
        rocket = View.inflate(this, R.layout.intercom_flow_layout, null);
        regTouch();
        wm.addView(rocket, params);

    }

    private int downX;
    private int downY;

    private void regTouch() {
        rocket.setOnTouchListener(new View.OnTouchListener() {
            private int lastX;
            private int lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:// 按下 事件
                        downX = lastX = (int) event.getRawX();
                        downY = lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:// 移动 事件
                        int disX = (int) (event.getRawX() - lastX);
                        int disY = (int) (event.getRawY() - lastY);
                        params.x += disX;
                        params.y += disY;
                        wm.updateViewLayout(rocket, params);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_UP:// 抬起 事件
                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();
                        int upX = x - downX;
                        int upY = y - downY;
                        upX = Math.abs(upX);
                        upY = Math.abs(upY);

                        if (upX < 20 && upY < 20) {
                            //点击进入指定页面
                            onClick();

                        }
                      /*  if (x > width_phone / 2) {
                            //放手后移到右边
                            handler.sendEmptyMessage(WINDOWLEFT);
                        } else {
                            //移到左边
                            handler.sendEmptyMessage(WINDOWRIGHT);
                        }*/

                        break;
                }
                return true;
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == WINDOWLEFT) {
                params.x = width_phone;
            } else if (msg.what == WINDOWRIGHT) {
                params.x = 0;
            }

            wm.updateViewLayout(rocket, params);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        wm.removeView(rocket);
    }

    /**
     * 悬浮点击
     */
    private void onClick() {
       stopSelf();
    }
}
