package com.android.custview.ui;

import android.opengl.GLES20;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.android.custview.R;
import com.android.custview.view.EglHelper;
import com.android.custview.view.render.BaseSurfaceView;
import com.android.custview.view.render.EGlSurfaceView;
import com.android.zp.base.BaseActivity;

public class GLSurfaceViewActivity extends BaseActivity {

    private SurfaceView surfaceView;
    private EGlSurfaceView eGlSurfaceView;

    private boolean isFirstSurface = false;

    @Override
    public int getLayout() {
        return R.layout.activity_g_l_surface_view;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.when_dirty:
                eGlSurfaceView.setRenderMode(BaseSurfaceView.RENDERMODE_WHEN_DIRTY);
                break;

            case R.id.continuously:
                eGlSurfaceView.setRenderMode(BaseSurfaceView.RENDERMODE_CONTINUOUSLY);
                eGlSurfaceView.requestRender();
                break;
        }
    }

    @Override
    public void initView() {
        surfaceView = findViewById(R.id.surface);
        eGlSurfaceView = findViewById(R.id.eGlSurfaceView);
        if (isFirstSurface) {
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            EglHelper eglHelper = new EglHelper();
                            eglHelper.initEgl(holder.getSurface(), null);
                            while (true) {
                                GLES20.glViewport(0, 0, width, height);
                                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
                                GLES20.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
                                eglHelper.swapBuffers();
                                try {
                                    Thread.sleep(16);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });
        }

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
