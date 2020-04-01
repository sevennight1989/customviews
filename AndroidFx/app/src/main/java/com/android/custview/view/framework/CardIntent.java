package com.android.custview.view.framework;

import android.content.Intent;

/**
 * 集成原生intent主要能统一数据格式
 */
public class CardIntent extends Intent {

    private Class<? extends AbstractCardView> mClass;

    public void setCardViewClass(Class<? extends AbstractCardView> clazz) {
        mClass = clazz;
    }

    public Class<? extends AbstractCardView> getCardViewClass() {
        return mClass;
    }
}
