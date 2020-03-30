package com.android.custview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.android.custview.view.framework.AbstractCardView;
import com.android.custview.view.framework.CardIntent;

public class TopCardView extends AbstractCardView {
    public TopCardView(@NonNull Context context) {
        this(context, null);
    }

    public TopCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startInfo() {
        CardIntent intent = new CardIntent();
        intent.setCardViewClass(InfoCardView.class);
        startCardView(intent);
    }

    public void finish() {
        super.finish();
    }

    @Override
    protected void onCreate(CardIntent intent) {

    }

    @Override
    protected void onNewIntent(CardIntent intent) {

    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onDestroy() {

    }
}
