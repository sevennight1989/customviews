package com.java.util.chain;

import com.android.zp.base.KLog;

public class ResMd5CheckHandler extends ResHandler {
    @Override
    public void handle(ResHandlerChain resHandlerChain) {
        KLog.logI("ResMd5CheckHandler handle");
        onProcess(resHandlerChain,50);
        endProcess(resHandlerChain,false);
        callNextHandler(resHandlerChain);
    }

    @Override
    public void onProcess(ResHandlerChain resHandlerChain, int progress) {
        KLog.logI("ResMd5CheckHandler onProcess progress : " + progress);
    }

    @Override
    public void endProcess(ResHandlerChain resHandlerChain, boolean isEnd) {
        KLog.logI("ResMd5CheckHandler endProcess isEnd : " + isEnd);
    }
}
