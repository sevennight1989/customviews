package com.android.custview.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.android.custview.R;
import com.android.zp.base.BaseActivity;

public class ErrorMsgActivity extends BaseActivity {

    private TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_error_msg;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void initView() {
        errorTv = findViewById(R.id.error_msg);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String errorMsg = preferences.getString("error_message", "null");
        errorTv.setText(errorMsg);
    }

    @Override
    public void initData() {

    }
}