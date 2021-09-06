package com.android.custview.impl;

import android.content.Context;

import com.android.custview.inf.ChatService;
import com.android.zp.base.KLog;

public class ChatServiceImpl implements ChatService {
    @Override
    public void onSendOutMessage(String msg) {
        KLog.logI("onSendOutMessage: " + msg);
    }

    @Override
    public void onReceiveMessage(String msg) {
        KLog.logI("onReceiveMessage " + msg);
    }

    @Override
    public void onInit(Context context) {
        KLog.logI("onInit");
    }
}
