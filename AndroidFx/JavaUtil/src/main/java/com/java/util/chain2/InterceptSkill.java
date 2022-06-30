package com.java.util.chain2;

import com.android.zp.base.KLog;

public class InterceptSkill extends BaseInterceptImpl{

    private JobInterceptBean jobInterceptBean;

    public InterceptSkill(JobInterceptBean jobInterceptBean) {
        this.jobInterceptBean = jobInterceptBean;
    }

    @Override
    public void intercept(InterceptChain chain) {
        super.intercept(chain);
        if (jobInterceptBean.isNeedSkill) {
            KLog.logI("InterceptSkill 拦截");
        } else {
            KLog.logI("InterceptSkill 不拦截");
            chain.process();
        }
    }
}
