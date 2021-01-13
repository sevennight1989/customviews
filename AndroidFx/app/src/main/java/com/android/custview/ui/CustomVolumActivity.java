package com.android.custview.ui;

import com.android.custview.R;
import com.android.custview.view.CustomVolumControlBar;
import com.android.custview.widget.CommuteDialog;
import com.android.zp.base.BaseActivity;

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

    private CommuteDialog mCommuteDialog;
    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCommuteDialog = new CommuteDialog(this,mCommuteDialog);
    }
}
