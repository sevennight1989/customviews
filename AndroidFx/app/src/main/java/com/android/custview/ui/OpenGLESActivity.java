package com.android.custview.ui;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.android.custview.R;
import com.android.custview.render.BackgroundRender;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import androidx.annotation.NonNull;

public class OpenGLESActivity extends BaseActivity {

    private static ReadWriteLock rwLock = new ReentrantReadWriteLock();

    private GLSurfaceView glSurfaceView;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_open_glesactivity;
    }

    @Override
    public void initView() {
        glSurfaceView = findViewById(R.id.glSurfaceView);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new BackgroundRender());
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void initData() {
        HandlerThread thread = new HandlerThread("OpenGLES");
        thread.start();
        mHandler = new Handler(thread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                KLog.logI("handleMessage what: " + msg.what);
                switch (msg.what){
                    case 0:

                        break;

                    default:
                        break;

                }
                return false;
            }
        });
        sendMessage();

    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    private void sendMessage(){
        rwLock.readLock().lock();
        mHandler.sendEmptyMessage(1);
        rwLock.readLock().unlock();
    }
}