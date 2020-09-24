package com.android.custview.ui;


import com.android.custview.R;
import com.android.custview.view.MarqueeTextView;

public class MarqueeActivity extends BaseActivity {

    MarqueeTextView mq;
    @Override
    public int getLayout() {
        return R.layout.activity_marquee;
    }

    @Override
    public void initView() {
        mq = findViewById(R.id.mq);
        mq.startScroll();
    }

    @Override
    public void initData() {

    }
}
