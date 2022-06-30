package com.java.util.chain2;

import com.android.zp.base.KLog;

public class InterceptNewMember extends BaseInterceptImpl {

    private JobInterceptBean jobInterceptBean;

    public InterceptNewMember(JobInterceptBean jobInterceptBean) {
        this.jobInterceptBean = jobInterceptBean;
    }

    @Override
    public void intercept(InterceptChain chain) {
        super.intercept(chain);
        if (jobInterceptBean.isNewMember) {
            KLog.logI("InterceptNewMember 拦截 新用户，跳转新用户页面");
        } else {
            KLog.logI("InterceptNewMember 不拦截 非新用户");
            chain.process();
        }
    }

    public void resetNewMember() {
        if (mChain != null) {
            KLog.logI("InterceptNewMember resetNewMember");
            mChain.process();
        }
    }
}
