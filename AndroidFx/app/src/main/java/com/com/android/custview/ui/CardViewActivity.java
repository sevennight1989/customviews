package com.com.android.custview.ui;

import android.view.View;
import android.widget.TextView;

import com.android.custview.R;
import com.android.custview.view.InfoCardView;
import com.android.custview.view.TopCardView;
import com.android.custview.view.framework.CardIntent;
import com.android.custview.view.framework.CardViewController;
import com.android.custview.view.framework.FrameView;

public class CardViewActivity extends BaseActivity {

    private FrameView mRoot;

    @Override
    public int getLayout() {
        return R.layout.activity_card_view;
    }

    @Override
    public void initView() {
        mRoot = findViewById(R.id.root);
        CardViewController.getInstance().init(mRoot);
        startInfo();
    }


    public void startInfo() {
        CardIntent intent = new CardIntent();
        intent.setCardViewClass(TopCardView.class);
        CardViewController.getInstance().startCardView(intent);
    }

    @Override
    public void initData() {

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                TopCardView textView1 = new TopCardView(this);
                mRoot.addView(textView1);
                break;
            case R.id.add2:
                InfoCardView textView2 = new InfoCardView(this);
                mRoot.addView(textView2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CardViewController.getInstance().release();
    }
}
