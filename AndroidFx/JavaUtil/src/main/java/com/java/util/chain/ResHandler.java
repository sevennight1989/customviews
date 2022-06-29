package com.java.util.chain;

public abstract class ResHandler {

    public abstract void handle(ResHandlerChain resHandlerChain);

    public abstract void onProcess(ResHandlerChain resHandlerChain,int progress);

    public abstract void endProcess(ResHandlerChain resHandlerChain,boolean isEnd);

    public void callNextHandler(ResHandlerChain resHandlerChain) {
        ResHandlerChain newChain = new ResHandlerChain(resHandlerChain.getHandlerList(), resHandlerChain.getIndex() + 1);
        newChain.proceed();
    }

}
