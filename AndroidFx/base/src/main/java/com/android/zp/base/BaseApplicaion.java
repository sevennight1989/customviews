package com.android.zp.base;

import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

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
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("PengLog")
                .showThreadInfo(true)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
