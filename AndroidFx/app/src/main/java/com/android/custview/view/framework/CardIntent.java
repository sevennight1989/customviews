package com.android.custview.view.framework;

import android.content.Intent;

public class CardIntent extends Intent {

    private Class<? extends AbstractCardView> mClass;

    public void setCardViewClass(Class<? extends AbstractCardView> clazz) {
        mClass = clazz;
    }

    public Class<? extends AbstractCardView> getCardViewClass() {
        return mClass;
    }
}
