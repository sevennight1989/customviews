package com.com.android.custview.ui;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.custview.R;
import com.com.android.custview.KLog;
import com.com.android.custview.adapter.MainAdapter;
import com.com.android.custview.widget.SpacesItemDecoration;

public class MainActivity extends BaseActivity {

    private RecyclerView mRv;
    private MainAdapter mMainAdapter;

    private String[] items = {"自定义View1", "进度条变色", "自定义音量条", "自定义ViewGroup", "自定义拖拽"
            , "ListView侧滑"};

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
                        intent.setClass(MainActivity.this, ProgressBarActivity.class);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, CustomVolumActivity.class);
                        break;
                    case 3:
                        intent.setClass(MainActivity.this, CustViewGroupActivity.class);
                        break;
                    case 4:
                        intent.setClass(MainActivity.this, DraggerActivity.class);
                        break;
                    case 5:
                        intent.setClass(MainActivity.this, ListActivity.class);
                        break;
                }

                startActivity(intent);
            }
        });
        mRv.addItemDecoration(new SpacesItemDecoration(20));
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mMainAdapter);
    }
}
