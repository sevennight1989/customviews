package com.java.util.chain2;

import com.android.zp.base.KLog;

public class InterceptMemberApprove extends BaseInterceptImpl{

    private JobInterceptBean jobInterceptBean;

    public InterceptMemberApprove(JobInterceptBean jobInterceptBean) {
        this.jobInterceptBean = jobInterceptBean;
    }

    @Override
    public void intercept(InterceptChain chain) {
        super.intercept(chain);
        if (!jobInterceptBean.isMemberApprove) {
            KLog.logI("InterceptMemberApprove 拦截");
        } else {
            KLog.logI("InterceptMemberApprove 不拦截");
            chain.process();
        }
    }
}
