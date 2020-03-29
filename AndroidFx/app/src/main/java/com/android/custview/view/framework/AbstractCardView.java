package com.android.custview.view.framework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.android.custview.view.InfoCardView;

public class AbstractCardView extends FrameLayout {


    public AbstractCardView(@NonNull Context context) {
        this(context,null);
    }

    public AbstractCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AbstractCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startCardView(Class<? extends AbstractCardView> clazz){
        CardViewController.getInstance().startCardView(clazz);
    }


    public void finish(){
        CardViewController.getInstance().removeCardView(getClass());
    }
}
