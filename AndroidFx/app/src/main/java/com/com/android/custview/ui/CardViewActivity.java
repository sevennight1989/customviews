package com.com.android.custview.ui;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.android.custview.R;
import com.android.custview.view.framework.CardViewController;

public class CardViewActivity extends BaseActivity {

    private FrameLayout mRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        mRoot = findViewById(R.id.root);
        CardViewController.getInstance().init(mRoot);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_card_view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
