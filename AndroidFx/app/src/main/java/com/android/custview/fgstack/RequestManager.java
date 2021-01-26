package com.android.custview.fgstack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.android.zp.base.KLog;

import java.util.Random;


public class RequestManager {

    public static final String ACTION_KEY = "action";
    public static final String ACTION_LOADING = "loading";
    public static final String ACTION_SUCCESS = "success";
    public static final String ACTION_FAILED = "failed";

    private Handler mHandler;

    private static class SingletonHolder {
        static RequestManager instance = new RequestManager();
    }

    private RequestManager() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static RequestManager getInstance() {
        return SingletonHolder.instance;
    }

    public interface RequestCallBack {
        void onRequestEnd(Bundle bundle);
    }

    public void requestPoiList(String key, RequestCallBack requestCallBack) {
        KLog.logI("requestPoiList: " + key);
        Bundle b = new Bundle();
        RequestParam requestParam = new RequestParam(RequestParam.REQUEST_TYPE_POI_LIST, new String[]{key});
        b.putParcelable("data", requestParam);
        b.putString(ACTION_KEY, ACTION_LOADING);
        requestCallBack.onRequestEnd(b);
        //模拟搜索poi
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int ret = new Random().nextInt(100);
                Bundle b = new Bundle();
                KLog.logE("ret = " + ret);
                if (ret % 2 == 0) {
                    b.putString(ACTION_KEY, ACTION_SUCCESS);
                } else {
                    b.putString(ACTION_KEY, ACTION_FAILED);
                }
                requestCallBack.onRequestEnd(b);
            }
        }, 2000);
    }

}
