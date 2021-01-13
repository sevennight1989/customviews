package com.android.zp.base;

import android.util.Log;

public class KLog {

    private static final String TAG = "PengLog";

    private static boolean ENABLE_LOG = true;

    public static void logI(String str){
        if(ENABLE_LOG){
            Log.i(TAG,str);
        }
    }

    public static void logE(String str){
        if(ENABLE_LOG){
            Log.e(TAG,str);
        }
    }
}

