package com.android.custview.view.render;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.android.custview.view.EglHelper;
import com.android.zp.base.KLog;

import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLContext;

public abstract class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Surface surface;
    private EGLContext eglContext;

    private EGLThread eglThread;
    private EGLRender eglRender;

    /**
     * 视图更新模式
     * RENDERMODE_WHEN_DIRTY    懒更新
     * RENDERMODE_CONTINUOUSLY  主动更新
     **/
    public final static int RENDERMODE_WHEN_DIRTY = 0;
    public final static int RENDERMODE_CONTINUOUSLY = 1;

    private int mRenderMode = RENDERMODE_CONTINUOUSLY;


    public BaseSurfaceView(Context context) {
        this(context, null);
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    public void setRender(EGLRender eglRender) {
        this.eglRender = eglRender;
    }

    public void setRenderMode(int mRenderMode) {

        if (eglRender == null) {
            throw new RuntimeException("must set render before");
        }
        this.mRenderMode = mRenderMode;
    }

    public void setSurfaceAndEglContext(Surface surface, EGLContext eglContext) {
        this.surface = surface;
        this.eglContext = eglContext;
    }

    public EGLContext getEglContext() {
        if (eglThread != null) {
            return eglThread.getEglContext();
        }
        return null;
    }

    public void requestRender() {
        if (eglThread != null) {
            eglThread.requestRender();
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        KLog.logI("surfaceCreated");
        if (surface == null) {
            surface = holder.getSurface();
        }
        eglThread = new EGLThread(new WeakReference<BaseSurfaceView>(this));
        eglThread.isCreate = true;
        eglThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        KLog.logI("surfaceChanged");
        eglThread.width = width;
        eglThread.height = height;
        eglThread.isChange = true;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        KLog.logI("surfaceDestroyed");
        eglThread.onDestroy();
        eglThread = null;
        surface = null;
        eglContext = null;
    }

    public interface EGLRender {

        /**
         * 视图创建时
         **/
        void onSurfaceCreated();

        /**
         * 视图发生变化时
         *
         * @param width  当前视图宽度
         * @param height 当前视图高度
         **/
        void onSurfaceChanged(int width, int height);

        /**
         * 数据更新时
         **/
        void onDrawFrame();
    }

    /**
     * 维护egl生命周期的线程
     **/
    public static class EGLThread extends Thread {

        private WeakReference<BaseSurfaceView> eglSurfaceViewWeakReference;
        private EglHelper eglHelper = null;
        private Object object = null;

        private boolean isExit = false;
        private boolean isCreate = false;
        private boolean isChange = false;
        private boolean isStart = false;

        private int width;
        private int height;

        public EGLThread(WeakReference<BaseSurfaceView> eglSurfaceViewWeakReference) {
            this.eglSurfaceViewWeakReference = eglSurfaceViewWeakReference;
        }

        @Override
        public void run() {
            super.run();
            isExit = false;
            isStart = false;
            object = new Object();
            eglHelper = new EglHelper();
            eglHelper.initEgl(eglSurfaceViewWeakReference.get().surface, eglSurfaceViewWeakReference.get().eglContext);

            while (true) {
                if (isExit) {
                    //释放资源
                    release();
                    break;
                }

                if (isStart) {
                    if (eglSurfaceViewWeakReference.get().mRenderMode == RENDERMODE_WHEN_DIRTY) {
                        synchronized (object) {
                            try {
                                KLog.logI("2222");
                                object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (eglSurfaceViewWeakReference.get().mRenderMode == RENDERMODE_CONTINUOUSLY) {
                        try {
                            KLog.logI("3333");
                            Thread.sleep(1000 / 60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        throw new RuntimeException("mRenderMode is wrong value");
                    }
                }
                KLog.logI("1111");
                onCreate();
                onChange(width, height);
                onDraw();

                isStart = true;

            }

        }

        private void onCreate() {
            if (isCreate && eglSurfaceViewWeakReference.get().eglRender != null) {
                isCreate = false;
                eglSurfaceViewWeakReference.get().eglRender.onSurfaceCreated();
            }
        }

        private void onChange(int width, int height) {
            if (isChange && eglSurfaceViewWeakReference.get().eglRender != null) {
                isChange = false;
                eglSurfaceViewWeakReference.get().eglRender.onSurfaceChanged(width, height);
            }
        }

        private void onDraw() {
            if (eglSurfaceViewWeakReference.get().eglRender != null && eglHelper != null) {
                eglSurfaceViewWeakReference.get().eglRender.onDrawFrame();
                if (!isStart) {
                    eglSurfaceViewWeakReference.get().eglRender.onDrawFrame();
                }
                eglHelper.swapBuffers();

            }
        }

        /**
         * 通知更新视图
         **/
        private void requestRender() {
            if (object != null) {
                synchronized (object) {
                    object.notifyAll();
                }
            }
        }

        public void onDestroy() {
            isExit = true;
            //通知更新视图
            requestRender();
        }

        /**
         * 释放egl环境
         **/
        public void release() {
            if (eglHelper != null) {
                eglHelper.destroyEgl();
                eglHelper = null;
                object = null;
                eglSurfaceViewWeakReference = null;
            }
        }

        public EGLContext getEglContext() {
            if (eglHelper != null) {
                return eglHelper.getEglContext();
            }
            return null;
        }

    }


}