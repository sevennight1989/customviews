package com.android.custview.inf;

import com.android.zp.base.module.ModuleService;

public interface ChatService extends ModuleService {
    //发送消息
    void onSendOutMessage(String msg);
    //接收到消息
    void onReceiveMessage(String msg);
}
