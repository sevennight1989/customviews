package com.android.custview.fgstack;


import android.view.View;


import com.android.custview.R;
import com.android.custview.fgstack.fg.Fg01;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.FragmentStackManager;
import com.android.zp.base.KLog;

public class FragmentStackActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_fragment_stack;
    }

    @Override
    public void initView() {
        FragmentStackManager.FragmentConfig fragmentConfig = new FragmentStackManager.FragmentConfig(getSupportFragmentManager(), R.id.fg_holder, FgConstant.NAV_STACK);
        FragmentStackManager.getInstance().setGlobalConfig(fragmentConfig);
        FragmentStackManager.getInstance().add(Fg01.class);
    }

    //模拟外部事件切换Fragment
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.poi_list_bt:
                KLog.logE(FragmentStackManager.getInstance().getCurrentFragmentName());
                break;

            case R.id.poi_detail_bt:
                KLog.logE(FragmentStackManager.getInstance().getCurrentFragment().getClass().getSimpleName());
                break;
        }
    }

    @Override
    public void initData() {

    }

}
