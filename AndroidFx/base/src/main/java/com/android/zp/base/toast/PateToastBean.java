package com.android.zp.base.toast;

import android.view.View;
import android.view.WindowManager;

public class PateToastBean implements Comparable<PateToastBean> {
    //toast 优先级越小越大
    private int priority = 5;

    private View layoutView;

    private WindowManager.LayoutParams params;
    private String content;
    private long delayTime=3000;

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PateToastBean() {
    }

    public PateToastBean(View layoutView) {
        this.layoutView = layoutView;
    }

    public PateToastBean(View layoutView, int priority) {
        this.priority = priority;
        this.layoutView = layoutView;
    }

    public WindowManager.LayoutParams getParams() {
        return params;
    }

    public void setParams(WindowManager.LayoutParams params) {
        this.params = params;
    }

    public View getLayoutView() {
        return layoutView;
    }

    public void setLayoutView(View layoutView) {
        this.layoutView = layoutView;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    @Override
    public int compareTo(PateToastBean toastBean) {
        if(priority == toastBean.getPriority()) {
            return 0;
        } else {
            return priority > toastBean.getPriority() ? 1 : -1;
        }
    }

    @Override
    public String toString() {
        return "PateToastBean{" +
                "priority=" + priority +
                ", layoutView=" + layoutView +
                ", params=" + params +
                '}';
    }
}
