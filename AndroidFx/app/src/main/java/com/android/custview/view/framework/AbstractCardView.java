package com.android.custview.view.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class AbstractCardView extends FrameLayout implements View.OnClickListener {


    public AbstractCardView(@NonNull Context context) {
        this(context, null);
    }

    public AbstractCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbstractCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startCardView(CardIntent intent) {
        CardViewController.getInstance().startCardView(intent);
    }


    public void finish() {
        CardViewController.getInstance().removeCardView(getClass());
    }

    protected abstract void onCreate(CardIntent intent);

    protected abstract void onNewIntent(CardIntent intent);

    public void pause() {
        setVisibility(View.GONE);
        onPause();
    }

    protected abstract void onPause();

    public void restart() {
        setVisibility(View.VISIBLE);
        onRestart();
    }

    protected abstract void onRestart();

    protected abstract void onDestroy();

    protected abstract void performClick(View view);

    @Override
    public void onClick(View v) {
        performClick(v);
    }
}
