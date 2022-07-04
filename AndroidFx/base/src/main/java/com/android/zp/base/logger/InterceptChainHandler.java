package com.android.zp.base.logger;

public class InterceptChainHandler <T>{
    InterceptChain interceptFirst;

    public void add(InterceptChain interceptChain){
        if(interceptFirst == null){
            interceptFirst = interceptChain;
            return;
        }
        InterceptChain node = interceptFirst;
        while (true){
            if(node.next == null){
                node.next = interceptChain;
                break;
            }
            node = node.next;
        }
    }

    public void intercept(T data){
        interceptFirst.intercept(data);
    }
}
