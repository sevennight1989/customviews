package com.java.util.chain2;


import com.android.zp.base.KLog;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class InterceptChain {

    private FragmentActivity activity;

    private Fragment fragment;

    private List<Interceptor> interceptors;

    private int index = 0;

    private InterceptChain(FragmentActivity activity, Fragment fragment, List<Interceptor> interceptors) {
        this.activity = activity;
        this.fragment = fragment;
        this.interceptors = interceptors;
    }


    public static Builder create(int count){
        return new Builder(count);
    }

    public void process() {
        if (interceptors != null) {
            if (index < interceptors.size()) {
                Interceptor interceptor = interceptors.get(index);
                index++;
                interceptor.intercept(this);
            } else {
                clearAllInterceptors();
            }
        }
    }

    public void clearAllInterceptors() {
        if (interceptors != null) {
            interceptors.clear();
            interceptors = null;
            KLog.logE("clearAllInterceptors");
        }
    }

    public static class Builder {

        private FragmentActivity activity;
        private final List<Interceptor> interceptors;
        private Fragment fragment;

        public Builder(int count) {
            interceptors = new ArrayList<>(count);
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (!interceptors.contains(interceptor)) {
                interceptors.add(interceptor);
            }
            return this;
        }

        public Builder attach(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder attach(FragmentActivity activity) {
            this.activity = activity;
            return this;
        }

        public InterceptChain build() {
            return new InterceptChain(activity, fragment, interceptors);
        }

    }
}
