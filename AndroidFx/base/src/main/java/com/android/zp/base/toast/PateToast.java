package com.android.zp.base.toast;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.android.zp.base.R;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;


final public class PateToast {

    private static final String TAG = PateToast.class.getSimpleName();

    private static PateToast mInstance;

    private static final int DELAY_REMOVE_VIEW = 3000;

    private static final int SHOW_WINDOW = 1;

    private static final int CLOSE_WINDOW = 2;

    private WindowManager mWindowManager;

    private static Object mLock = new Object();

    private CopyOnWriteArrayList<PateToastBean> mPendingDisPlayList = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<PateToastBean> mDisPlayingList = new CopyOnWriteArrayList<>();
    private long delayTime=DELAY_REMOVE_VIEW;
    private Handler mHandler = new Handler(Looper.getMainLooper(), (Message msg) -> {
        switch (msg.what) {
            case SHOW_WINDOW:
                show((PateToastBean) msg.obj);
                break;
            case CLOSE_WINDOW:
                close((PateToastBean) msg.obj);
                break;
        }
        return false;
    });

    public static PateToast getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                mInstance = new PateToast();
            }
        }
        return mInstance;

    }

    private PateToast() {

    }
    private View mRootView;
    private TextView tvContent;
    public Builder setContentView(Context context) {
        if (mRootView==null) {
            mRootView = LayoutInflater.from(context).inflate(R.layout.default_toast, null);
            tvContent=mRootView.findViewById(R.id.toast_text);
        }
        PateToastBean bean = new PateToastBean();
        bean.setLayoutView(mRootView);
        this.setContentView(context, bean);
        return new Builder(bean);
    }
    public Builder setContentView(Context context,int priority) {
        if (mRootView==null) {
            mRootView = LayoutInflater.from(context).inflate(R.layout.default_toast, null);
            tvContent=mRootView.findViewById(R.id.toast_text);
        }
        PateToastBean bean = new PateToastBean();
        bean.setLayoutView(mRootView);
        bean.setPriority(priority);
        this.setContentView(context, bean);
        return new Builder(bean);
    }

    public Builder setCustomContentView(Context context, PateToastBean bean) {
        if (mRootView==null) {
            mRootView = LayoutInflater.from(context).inflate(R.layout.default_toast, null);
            tvContent=mRootView.findViewById(R.id.toast_text);
        }
        bean.setLayoutView(mRootView);
        this.setContentView(context, bean);
        return new Builder(bean);
    }

    private PateToast setContentView(Context context, PateToastBean bean) {
        if (bean == null) {
            throw new RuntimeException("PateToastBean can not null");
        }
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        prepareData(bean);
        return this;
    }

    private void prepareData(PateToastBean bean) {
        initParams(bean);
        addBeanToList(bean);
    }

    private void addBeanToList(PateToastBean bean) {
        if (mPendingDisPlayList != null) {
            mPendingDisPlayList.add(bean);
            Collections.sort(mPendingDisPlayList);
        }
    }

    private void initParams(PateToastBean bean) {
        WindowManager.LayoutParams params = bean.getParams();
        if (params == null) {
            params = new WindowManager.LayoutParams();
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM |
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED |
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            params.gravity = Gravity.CENTER;                                       //设置窗口的位置
            params.format = PixelFormat.TRANSLUCENT;                               //不设置这个弹出框的透明遮罩显示为黑色
            params.width = 864;                //窗口的宽
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.setTitle("PateToast");
            params.windowAnimations = R.style.toast_dialog_anim;
        } else {
            params = bean.getParams();
        }
        params.type=WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;//2018;
//        getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        bean.setParams(params);
    }

    public void showPateAlertDialog() {
        delayTime=DELAY_REMOVE_VIEW;
        PateToastBean bean = mPendingDisPlayList.get(0);
        if (bean == null) return;
        switch (getDisPlayAction(bean)) {
            case 1: // update
                mDisPlayingList.remove(0);
                doRealDisPlay(bean);
                Log.d(TAG, "getAdasMediaPlayer interrupt&play");
                break;
            case 2: // wait
                doRealDisPlay(bean);
                Log.d(TAG, "getAdasMediaPlayer reset player");
                break;
            case 3: // wait
                //处理逻辑已经放close()中了
                Log.d(TAG, "getAdasMediaPlayer wait&play");
                break;
            default:
        }
    }

    public void showPateAlertDialog(long time) {
        delayTime=time;
        PateToastBean bean = mPendingDisPlayList.get(0);
        if (bean == null) return;
        switch (getDisPlayAction(bean)) {
            case 1: // update

                mDisPlayingList.remove(0);
                doRealDisPlay(bean);
                Log.d(TAG, "getAdasMediaPlayer interrupt&play");
                break;
            case 2: // wait
                doRealDisPlay(bean);
                Log.d(TAG, "getAdasMediaPlayer reset player");
                break;
            case 3: // wait
                //处理逻辑已经放close()中了
                Log.d(TAG, "getAdasMediaPlayer wait&play");
                break;
            default:
        }
    }
    private void show(PateToastBean bean) {
        if (bean.getLayoutView() == null) return;
//        Utils.hideInputMethod(ContextUtils.context);
        if (!bean.getLayoutView().isShown()) {
            mWindowManager.addView(bean.getLayoutView(), bean.getParams());
        } else {
            mWindowManager.updateViewLayout(bean.getLayoutView(), bean.getParams());
        }
        if (tvContent!=null){
            tvContent.setText(bean.getContent());
        }else{
            if (mRootView!=null){
                tvContent=mRootView.findViewById(R.id.toast_text);
                tvContent.setText(bean.getContent());
            }
        }
        mHandler.removeMessages(CLOSE_WINDOW);
        Message message = Message.obtain();
        message.what = CLOSE_WINDOW;
        message.obj = bean;
        mHandler.sendMessageDelayed(message, delayTime);
    }

    private void doRealDisPlay(final PateToastBean bean) {
        if (bean == null) {
            Log.e(TAG, "doRealDisPlay bean is null");
            return;
        }
        mPendingDisPlayList.remove(bean);
        mDisPlayingList.add(bean);
        Collections.sort(mDisPlayingList);
        Message message = Message.obtain();
        message.what = SHOW_WINDOW;
        message.obj = bean;
        mHandler.sendMessage(message);
    }

    // 1 interrupt&show;2 create show;3 waiting & pending
    private int getDisPlayAction(PateToastBean info) {
        //dumpList();
        if (mDisPlayingList.size() <= 0) {
            return 2;
        } else {
            if ((info.getPriority() - getPriorestInPlayingList()) < 0) {
                return 1;
            }
            if ((info.getPriority() - getPriorestInPlayingList()) == 0) {
                return 2;
            }
            if ((info.getPriority() - getPriorestInPlayingList()) > 0) {
                return 3;
            }
        }
        return -1;
    }

    private int getPriorestInPlayingList() {
        if (mDisPlayingList.size() <= 0) {
            return -1;
        }
        return mDisPlayingList.get(0).getPriority();
    }

    public void closeAllPateAlertDialog(){
        mPendingDisPlayList.clear();
        if (mDisPlayingList.size()>0){
            for (PateToastBean pateToastBean : mDisPlayingList) {
                if (pateToastBean.getLayoutView().isShown()){
                    mWindowManager.removeViewImmediate(pateToastBean.getLayoutView());
                }
            }
            mDisPlayingList.clear();
        }
    }
    public void closeAllDisplayingPateAlertDialog(){
        if (mDisPlayingList.size()>0){
            for (PateToastBean pateToastBean : mDisPlayingList) {
                if (pateToastBean.getLayoutView().isShown()){
                    mWindowManager.removeViewImmediate(pateToastBean.getLayoutView());
                }
            }
            mDisPlayingList.clear();
        }
    }
    private void close(PateToastBean bean) {
        if (bean.getLayoutView().isShown()) {
            mWindowManager.removeViewImmediate(bean.getLayoutView());
        }
        if (mDisPlayingList.size() > 0) {
            mDisPlayingList.remove(0);
            //等待播放
            if (mPendingDisPlayList.size() > 0) {
                Log.d(TAG, "close mPendingDisPlayList.get(0)=" + mPendingDisPlayList.get(0));
                doRealDisPlay(mPendingDisPlayList.get(0));
            }
        } else {
            if (mPendingDisPlayList.size() > 0) {
                doRealDisPlay(mPendingDisPlayList.get(0));
            }
        }
    }

    public class Builder {

        private PateToastBean bean;
        public Builder(PateToastBean bean) {
            this.bean=bean;
        }

        public Builder setTextView(String content) {
             bean.setContent(content);
            return this;
        }

        public void showPateAlertDialog() {
            PateToast.this.showPateAlertDialog();
        }
        public void showPateAlertDialog(long time) {
            PateToast.this.showPateAlertDialog(time);
        }
    }
}
