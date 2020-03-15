package com.com.android.custview.ui;

import com.android.custview.R;
import com.android.custview.view.CustomVolumControlBar;

public class CustomVolumActivity extends BaseActivity {
    private CustomVolumControlBar cvBar;
    @Override
    public int getLayout() {
        return R.layout.activity_custom_volum;
    }

    @Override
    public void initView() {
        cvBar = findViewById(R.id.cvCBar);
    }

    @Override
    public void initData() {

    }
}
