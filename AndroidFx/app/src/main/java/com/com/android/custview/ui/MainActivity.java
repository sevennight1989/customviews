package com.com.android.custview.ui;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.custview.R;
import com.com.android.custview.KLog;
import com.com.android.custview.adapter.MainAdapter;

public class MainActivity extends BaseActivity {

    private RecyclerView mRv;
    private MainAdapter mMainAdapter;

    private String[] items = {"自定义View1", "自定义View2"};

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mRv = findViewById(R.id.rv);
    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public void initData() {
        mMainAdapter = new MainAdapter(this);
        mMainAdapter.setData(items);
        mMainAdapter.setOnClickListener(new MainAdapter.OnClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent();
                KLog.logD("点击了 " + pos);
                switch (pos) {
                    case 0:
                        intent.setClass(MainActivity.this, CustView01Activity.class);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, CustView01Activity.class);
                        break;
                }

                startActivity(intent);
            }
        });
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mMainAdapter);
    }
}
