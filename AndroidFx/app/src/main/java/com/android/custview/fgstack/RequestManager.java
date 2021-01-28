package com.android.custview.fgstack;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.android.zp.base.KLog;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class RequestManager implements Handler.Callback {

    public static final String ACTION_KEY = "action";
    public static final String ACTION_LOADING = "loading";
    public static final String ACTION_SUCCESS = "success";
    public static final String ACTION_FAILED = "failed";

    private static final int REQ_POI_LIST = 0;

    private Queue<String> mReqQueue = new LinkedList<>();

    public boolean isCancel = true;

    private Handler workHandler;
    private Handler mainHandler = new Handler(Looper.getMainLooper(), this);
    private HandlerThread pThread;

    private static class SingletonHolder {
        static RequestManager instance = new RequestManager();
    }

    private RequestManager() {
        pThread = new HandlerThread("p-thread", Process.THREAD_PRIORITY_BACKGROUND);
        pThread.start();
        workHandler = new Handler(pThread.getLooper(), this);
    }

    public static RequestManager getInstance() {
        return SingletonHolder.instance;
    }

    private void sendMessage(Message msg) {
        sendMessageDelay(msg, 0);
    }

    private void sendMessageDelay(Message msg, long delay) {
        if (pThread.isAlive() && !pThread.isInterrupted()) {
            workHandler.sendMessageDelayed(msg, delay);
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        int what = msg.what;
        KLog.logI("handleMessage: " + what + "    " + Thread.currentThread().getName());
        switch (what) {
            case REQ_POI_LIST:
                //这里模拟耗时操作
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int ret = new Random().nextInt(100);
                Bundle b = new Bundle();
                KLog.logE("ret = " + ret);
                if (ret % 2 == 0) {
                    b.putString(ACTION_KEY, ACTION_SUCCESS);
                } else {
                    b.putString(ACTION_KEY, ACTION_FAILED);
                }
                String sessionId = msg.obj.toString();
                KLog.logI("handleMessage sessionId: " + sessionId);
                if (!isCancel && TextUtils.equals(sessionId, mReqQueue.peek())) {
                    mReqQueue.poll();
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mRequestCallBack.onRequestEnd(b);
                        }
                    });
                }
                break;
        }
        return false;
    }

    private Message getMessage(int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        return msg;
    }

    public interface RequestCallBack {
        void onRequestEnd(Bundle bundle);
    }

    private RequestCallBack mRequestCallBack;

    public void requestPoiList(String key, RequestCallBack requestCallBack) {
        isCancel = false;
        String sessionId = new Random().nextInt(9999) + "";
        mReqQueue.offer(sessionId);
        KLog.logI("requestPoiList sessionId: " + sessionId + "  key" + key);
        mRequestCallBack = requestCallBack;
        Bundle b = new Bundle();
        RequestParam requestParam = new RequestParam(RequestParam.REQUEST_TYPE_POI_LIST, new String[]{key});
        b.putParcelable("data", requestParam);
        b.putString(ACTION_KEY, ACTION_LOADING);
        requestCallBack.onRequestEnd(b);
        sendMessage(getMessage(REQ_POI_LIST, sessionId));

        //模拟搜索poi

    }

    public void cancelRequest() {
        KLog.logI("cancelRequest");
        isCancel = true;
        mReqQueue.poll();
    }

}
