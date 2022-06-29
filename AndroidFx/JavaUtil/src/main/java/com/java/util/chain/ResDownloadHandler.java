package com.java.util.chain;


import com.android.zp.base.KLog;

public class ResDownloadHandler extends ResHandler {
    @Override
    public void handle(ResHandlerChain resHandlerChain) {
        KLog.logI("ResDownloadHandler handle");
        onProcess(resHandlerChain,10);
        endProcess(resHandlerChain,false);
        callNextHandler(resHandlerChain);

    }

    @Override
    public void onProcess(ResHandlerChain resHandlerChain, int progress) {
        KLog.logI("ResDownloadHandler onProcess progress : " + progress);
    }

    @Override
    public void endProcess(ResHandlerChain resHandlerChain, boolean isEnd) {
        KLog.logI("ResDownloadHandler endProcess isEnd : " + isEnd);
    }
}
