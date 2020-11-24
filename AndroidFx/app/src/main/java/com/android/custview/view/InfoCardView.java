package com.android.custview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.custview.R;
import com.android.custview.view.framework.AbstractCardView;
import com.android.custview.view.framework.CardIntent;
import com.com.android.custview.KLog;

public class InfoCardView extends AbstractCardView {
    private Button mCloseBt;

    public InfoCardView(@NonNull Context context) {
        this(context, null);
    }

    public InfoCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.info_card_layout, this, true);
        mCloseBt = findViewById(R.id.close);
        mCloseBt.setOnClickListener(this);
        KLog.logI("InfoCardView Call Construct Method");
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
    protected void onRestart() {
    }

    @Override
    protected void onDestroy() {

    }

    @Override
    protected void performClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
        }
    }
}
