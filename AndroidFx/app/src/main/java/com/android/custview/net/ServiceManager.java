package com.android.custview.net;

import android.content.Context;

public class ServiceManager {

   private String TAG = ServiceManager.class.getSimpleName();

    private final static int READ_TIMEOUT = 100;

    private final static int CONNECT_TIMEOUT = 60;

    private final static int WRITE_TIMEOUT = 60;
    //上下文
    private Context mContext;

    //接口base URL
    private String mBaseUrl;
}
