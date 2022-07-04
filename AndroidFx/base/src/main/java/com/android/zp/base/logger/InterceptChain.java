package com.android.zp.base.logger;

public abstract class InterceptChain<T> {

    public InterceptChain next;

    public void intercept(T data){
        if(next != null){
            next.intercept(data);
        }
    }
}
