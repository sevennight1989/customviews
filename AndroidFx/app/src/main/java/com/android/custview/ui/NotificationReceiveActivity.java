package com.android.custview.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.custview.R;
import com.android.custview.utils.KLog;

public class NotificationReceiveActivity extends BaseActivity {

    private TextView mUrlContentText;

    @Override
    public int getLayout() {
        return R.layout.activity_notification_receive;
    }

    @Override
    public void initView() {
        mUrlContentText = findViewById(R.id.url_content);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            if (TextUtils.isEmpty(url)) {
                KLog.logE("Url is null!!!");
                return;
            }
            KLog.logI("Url: " + url);
            mUrlContentText.setText(url);
        }
    }
}
