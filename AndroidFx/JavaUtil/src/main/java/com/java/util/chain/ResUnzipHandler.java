package com.java.util.chain;

import com.android.zp.base.KLog;

public class ResUnzipHandler extends ResHandler{
    @Override
    public void handle(ResHandlerChain resHandlerChain) {
        KLog.logI("ResUnzipHandler handle");
        onProcess(resHandlerChain, 90);
        endProcess(resHandlerChain, true);
        callNextHandler(resHandlerChain);
    }

    @Override
    public void onProcess(ResHandlerChain resHandlerChain, int progress) {
        KLog.logI("ResUnzipHandler onProcess progress : " + progress);
    }

    @Override
    public void endProcess(ResHandlerChain resHandlerChain, boolean isEnd) {
        KLog.logI("ResUnzipHandler endProcess isEnd : " + isEnd);
    }
}
