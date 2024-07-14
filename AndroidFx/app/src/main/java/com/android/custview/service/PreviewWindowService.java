package com.android.custview.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;

import com.android.custview.R;

import java.io.IOException;

import androidx.annotation.Nullable;

public class PreviewWindowService extends Service {
    private static final String TAG = "PreviewWindowService";
    private WindowManager wm;
    private WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    private View rocket;
    private int width_phone, height_phone;
    public Camera mCamera;
    private TextureView textureView;
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
        params.x = 0;
        params.y = 0;
        params.height = height_phone;
        params.width = width_phone;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;

        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        params.setTitle("Itcast rocket");
        rocket = View.inflate(this, R.layout.preview_layout, null);

        textureView = rocket.findViewById(R.id.textureVIew);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                try {
                    mCamera = Camera.open(0);
                    mCamera.setDisplayOrientation(90);
                    mCamera.setPreviewTexture(surface);
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.release();
                    mCamera = null;
                }
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        wm.addView(rocket, params);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        wm.removeView(rocket);
    }

}
