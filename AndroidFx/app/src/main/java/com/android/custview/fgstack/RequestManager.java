package com.android.custview.fgstack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.android.custview.fgstack.fg.FgLoadingOrError;
import com.android.zp.base.FragmentStackManager;
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

    private RequestManager(){
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static RequestManager getInstance() {
        return SingletonHolder.instance;
    }

    public void requestPoiList(String key){
        KLog.logI("requestPoiList: " + key);
        Bundle b = new Bundle();
        b.putString("search_key",key);
        b.putString(ACTION_KEY,ACTION_LOADING);
        FragmentStackManager.getInstance().add(FgLoadingOrError.class,b);
        //模拟搜索poi
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int ret = new Random().nextInt(100);
                Bundle b = new Bundle();
                KLog.logE("ret = " + ret);
                if (ret % 2 == 0) {
                    b.putString(ACTION_KEY,ACTION_SUCCESS);
                } else {
                    b.putString(ACTION_KEY,ACTION_FAILED);
                }
                FragmentStackManager.getInstance().add(FgLoadingOrError.class,b);
            }
        },2000);
    }

}
