package com.android.custview.fgstack;


import android.view.View;


import com.android.custview.R;
import com.android.custview.fgstack.fg.Fg01;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.FragmentStackManager;
import com.android.zp.base.KLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FragmentStackActivity extends BaseActivity {


    @Override
    public int getLayout() {
        return R.layout.activity_fragment_stack;
    }

    @Override
    public void initView() {
        FragmentStackManager.FragmentConfig fragmentConfig = new FragmentStackManager.FragmentConfig(getSupportFragmentManager(), R.id.fg_holder);
        FragmentStackManager.getInstance().setGlobalConfig(this,fragmentConfig);
        FragmentStackManager.getInstance().setCallBack(new FragmentStackManager.Callback() {
            @Override
            public void onFragmentStackNull() {
                KLog.logI("onFragmentStackNull");
                finish();
            }
        });
        FragmentStackManager.getInstance().add(this,Fg01.class);
    }

    //模拟外部事件切换Fragment
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.poi_list_bt:
                KLog.logE(FragmentStackManager.getInstance().getCurrentFragmentName(this));
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
                for (int i = 1; i <= 10; i++) {
                    final int ii = i;
                    cachedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(ii * 10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            KLog.logI("线程名称：" + Thread.currentThread().getName() + "，执行" + ii);
                        }
                    });
                }
                break;

            case R.id.poi_detail_bt:
                KLog.logE(FragmentStackManager.getInstance().getCurrentFragment(this).getClass().getSimpleName());
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
                for (int i = 0; i < 10; i++) {
                    final int ii = i;
                    fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            KLog.logI("线程名称：" + Thread.currentThread().getName() + "，执行" + ii);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                }
                break;
        }
    }

    @Override
    public void initData() {
    }

    @Override
    public void onBackPressed() {
        KLog.logI("onBackPressed");
        FragmentStackManager.getInstance().finish(this);
    }
}
