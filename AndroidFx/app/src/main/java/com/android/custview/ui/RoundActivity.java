package com.android.custview.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.android.custview.R;
import com.android.custview.service.IntercomTimeWindowService;
import com.android.custview.service.PreviewWindowService;
import com.android.custview.view.CustRoundView;
import com.android.zp.base.BaseActivity;
import com.ss.ugc.android.alpha_player.widget.GLTextureView;

import java.io.IOException;

public class RoundActivity extends BaseActivity {
    private CustRoundView custRoundView;
//    private TextureView textureView;

//    public Camera mCamera;

    @Override
    public int getLayout() {
        return R.layout.activity_round;
    }

    @Override
    public void initView() {
//        custRoundView = findViewById(R.id.customRoundView);
//        textureView = findViewById(R.id.textureVIew);
//        new PreviewDialog(this).show();
        startService(new Intent(this, PreviewWindowService.class));
    }

    @Override
    public void initData() {
//        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
//            @Override
//            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//                try {
//                    mCamera = Camera.open(0);
//                    mCamera.setDisplayOrientation(90);
//                    mCamera.setPreviewTexture(surface);
//                    mCamera.startPreview();
//                } catch (IOException e) {
//                    e.printStackTrace();
//
//                }
//            }
//
//            @Override
//            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//
//            }
//
//            @Override
//            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//                if (mCamera != null) {
//                    mCamera.stopPreview();
//                    mCamera.release();
//                    mCamera = null;
//                }
//                return false;
//            }
//
//            @Override
//            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//            }
//        });
    }
/*        textureView.setOpaque(true);
        textureView.setClipToOutline(true);
        textureView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
//                Rect rect = new Rect();
//                view.getGlobalVisibleRect(rect);
//                int lm = 0;
//                int tm = 0;
//                Rect selfRect = new Rect(lm, tm, rect.right - rect.left - lm, rect.bottom - rect.top - tm);
//                outline.setRoundRect(selfRect, 50);

                //不支持二阶贝塞尔曲线
//                path.moveTo(0, 500);
//                path.lineTo(0, 100);
//                path.quadTo(0, 0, 100, 0);
//                path.lineTo(400,0);
//                path.quadTo(500, 0, 500, 100);
//                path.lineTo(500,500);
//                path.close();
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                Path path = new Path();
//                RectF rectF = new RectF(rect);
//                float[] radius = new float[8];
//                radius[0] = 50f;
//                radius[1] = 70f;
//                radius[2] = 50f;
//                radius[3] = 70f;
//                radius[4] = 50f;
//                radius[5] = 70f;
//                radius[6] = 50f;
//                radius[7] = 70f;
//                path.addRoundRect(rectF, radius, Path.Direction.CCW);

                path.moveTo(0, 500);
                path.lineTo(0, 100);
                path.lineTo(400, 0);
                path.lineTo(500, 500);
                path.close();
                outline.setConvexPath(path);
            }
        });

    }*/

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//                custRoundView.updateView(event.getX(),event.getY());
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
}