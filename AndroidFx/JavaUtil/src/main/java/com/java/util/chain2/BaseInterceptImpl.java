package com.java.util.chain2;


public abstract class BaseInterceptImpl implements Interceptor {

    protected InterceptChain mChain;
    @Override
    public void intercept(InterceptChain chain) {
        mChain = chain;
    }
}
