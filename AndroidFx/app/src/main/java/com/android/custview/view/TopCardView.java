package com.android.custview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.android.custview.R;
import com.android.custview.view.framework.AbstractCardView;
import com.android.custview.view.framework.CardIntent;

public class TopCardView extends AbstractCardView {
    private Button mStartBt;

    public TopCardView(@NonNull Context context) {
        this(context, null);
    }

    public TopCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.top_card_layout, this, true);
        mStartBt = findViewById(R.id.start);
        mStartBt.setOnClickListener(this);
    }

    public void startInfo() {
        CardIntent intent = new CardIntent();
        intent.setCardViewClass(InfoCardView.class);
        startCardView(intent);
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

    @Override
    protected void performClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                startInfo();
                break;
        }
    }
}
