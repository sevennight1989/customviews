package com.android.custview.view.framework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class AbstractCardView extends FrameLayout {


    public AbstractCardView(@NonNull Context context) {
        this(context,null);
    }

    public AbstractCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AbstractCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startCardView(CardIntent intent){
        CardViewController.getInstance().startCardView(intent);
    }


    public void finish(){
        CardViewController.getInstance().removeCardView(getClass());
    }

    protected abstract void onCreate(CardIntent intent);

    protected abstract void onNewIntent(CardIntent intent);

    protected abstract void onPause();

    protected abstract void onDestroy();


}
