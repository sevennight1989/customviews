package com.android.zp.base;

import android.content.Context;

public class BaseApplicaion {

    private static Context mContext;

    private static class SingletonHolder {
        static BaseApplicaion instance = new BaseApplicaion();
    }

    public static BaseApplicaion getInstance() {
        return SingletonHolder.instance;
    }

    public Context getContext(){
        return mContext;
    }

    public void onCreate(Context context) {
        mContext  = context;
    }
}
