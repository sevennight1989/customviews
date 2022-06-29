package com.java.util.chain;

import com.android.zp.base.KLog;

import java.util.List;

public class ResHandlerChain {

    private final List<ResHandler> handlerList;
    private final int index;

    public ResHandlerChain(List<ResHandler> handlerList, int index) {
        this.handlerList = handlerList;
        this.index = index;
    }

    public List<ResHandler> getHandlerList() {
        return handlerList;
    }

    public int getIndex() {
        return index;
    }

    public void proceed() {
        int size = handlerList.size();
        if (index >= size) {
            KLog.logE("target index " + index + " over handler's size!!!");
            return;
        }
        ResHandler resHandler = handlerList.get(index);
        resHandler.handle(this);
    }
}
