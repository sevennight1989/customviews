package com.com.android.custview;

import android.util.Log;

public class KLog {

    private static final String TAG = "PengLog";

    private static boolean ENABLE_LOG = true;

    public static void logD(String str){
        if(ENABLE_LOG){
            Log.d(TAG,"-> " + str +" <-");
        }
    }
}

