package com.android.custview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLContext;

public class MyGLSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Surface surface;
    private EGLContext eglContext;
    private MyGLThread myGLThread;
    private MyGLRender myGLRender;

    public static final int RENDERMODE_WHEN_DIRTY = 0;
    public static final int RENDERMODE_CONTINUOUSLY = 1;

    public int mRenderMode = RENDERMODE_CONTINUOUSLY;

    public MyGLSurfaceView(Context context) {
        this(context, null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (surface == null) {
            surface = holder.getSurface();
        }
        myGLThread = new MyGLThread(new WeakReference<MyGLSurfaceView>(this));
        myGLThread.isCreate = true;
        myGLThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        myGLThread.width = width;
        myGLThread.height = height;
        myGLThread.isChange = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        myGLThread.onDestroy();
        myGLThread = null;
        surface = null;
        eglContext = null;
    }

    public void setSurfaceAndEglContext(Surface surface, EGLContext eglContext) {
        this.surface = surface;
        this.eglContext = eglContext;
    }

    public EGLContext getEGLContext() {
        if (myGLThread != null) {
            return myGLThread.getEglContext();
        }
        return null;
    }

    public void requestRender() {
        if (myGLThread != null) {
            myGLThread.requestRender();
        }
    }

    public void setMyRender(MyGLRender myGLRender) {
        this.myGLRender = myGLRender;
    }

    public void setRenderMode(int renderMode) {
        if (myGLRender == null) {
            throw new RuntimeException("must set render before");
        }
        mRenderMode = renderMode;
    }

    public interface MyGLRender {
        void onSurfaceCreated();

        void onSurfaceChanged(int width, int height);

        void onDrawFrame();
    }

    static class MyGLThread extends Thread {

        private WeakReference<MyGLSurfaceView> myGLSurfaceViewWeakReference;
        private MyEglHelper myEglHelper = null;
        private boolean isExit = false;
        private boolean isCreate = false;
        private boolean isChange = false;
        private boolean isStart = false;
        private Object object = null;
        private int width;
        private int height;

        public MyGLThread(WeakReference<MyGLSurfaceView> myGLSurfaceViewWeakReference) {
            this.myGLSurfaceViewWeakReference = myGLSurfaceViewWeakReference;
        }

        @Override
        public void run() {
            super.run();
            isExit = false;
            isStart = false;
            object = new Object();
            myEglHelper = new MyEglHelper();
            myEglHelper.initEgl(myGLSurfaceViewWeakReference.get().surface,
                    myGLSurfaceViewWeakReference.get().eglContext);
            while (true) {
                if (isExit) {
                    release();
                    break;
                }

                if (isStart) {
                    if (myGLSurfaceViewWeakReference.get().mRenderMode == RENDERMODE_WHEN_DIRTY) {
                        synchronized (object) {
                            try {
                                object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (myGLSurfaceViewWeakReference.get().mRenderMode == RENDERMODE_CONTINUOUSLY) {
                        try {
                            Thread.sleep(1000 / 60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        throw new RuntimeException("mRenderMode is error value");
                    }
                }
                onCreate();
                onChange(width, height);
                onDraw();
                isStart = true;
            }
        }

        private void onCreate() {
            if (isCreate && myGLSurfaceViewWeakReference.get().myGLRender != null) {
                isCreate = false;
                myGLSurfaceViewWeakReference.get().myGLRender.onSurfaceCreated();
            }
        }

        private void onChange(int width, int height) {
            if (isCreate && myGLSurfaceViewWeakReference.get().myGLRender != null) {
                isChange = false;
                myGLSurfaceViewWeakReference.get().myGLRender.onSurfaceChanged(width, height);
            }
        }

        private void onDraw() {
            if (myGLSurfaceViewWeakReference.get().myGLRender != null && myEglHelper != null) {
                myGLSurfaceViewWeakReference.get().myGLRender.onDrawFrame();
                if (!isStart) {
                    myGLSurfaceViewWeakReference.get().myGLRender.onDrawFrame();
                }
                myEglHelper.swapBuffers();
            }
        }

        public EGLContext getEglContext() {
            if (myEglHelper != null) {
                myEglHelper.getEglContext();
            }
            return null;
        }

        public void onDestroy() {
            isExit = true;
            requestRender();
        }

        private void requestRender() {
            if (object != null) {
                synchronized (object) {
                    notifyAll();
                }
            }
        }

        public void release() {
            if (myEglHelper != null) {
                myEglHelper.destroyEgl();
                myEglHelper = null;
                object = null;
                myGLSurfaceViewWeakReference = null;
            }
        }
    }
}
