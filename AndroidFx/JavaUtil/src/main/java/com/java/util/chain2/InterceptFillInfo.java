package com.java.util.chain2;

import com.android.zp.base.KLog;

public class InterceptFillInfo extends BaseInterceptImpl{

    private JobInterceptBean jobInterceptBean;

    public InterceptFillInfo(JobInterceptBean jobInterceptBean) {
        this.jobInterceptBean = jobInterceptBean;
    }

    @Override
    public void intercept(InterceptChain chain) {
        super.intercept(chain);
        if(!jobInterceptBean.isFillInfo){
            KLog.logI("InterceptFillInfo 拦截 跳转新页面");
        }else {
            KLog.logI("InterceptFillInfo 不拦截");
            chain.process();
        }
    }
}
