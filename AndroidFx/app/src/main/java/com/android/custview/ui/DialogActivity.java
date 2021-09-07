package com.android.custview.ui;



import com.android.custview.R;
import com.android.zp.base.BaseActivity;

public class DialogActivity extends BaseActivity {


    @Override
    public int getLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initView() {
        new SettingDialog(this).show();
    }

    @Override
    public void initData() {

    }
}
