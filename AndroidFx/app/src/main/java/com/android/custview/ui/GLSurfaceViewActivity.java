package com.android.custview.ui;

import android.opengl.GLES20;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.android.custview.R;
import com.android.custview.view.EglHelper;

public class GLSurfaceViewActivity extends BaseActivity {

    private SurfaceView surfaceView;

    private boolean isFirstSurface = false;

    @Override
    public int getLayout() {
        return R.layout.activity_g_l_surface_view;
    }

    @Override
    public void initView() {
        surfaceView = findViewById(R.id.surface);
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
}
