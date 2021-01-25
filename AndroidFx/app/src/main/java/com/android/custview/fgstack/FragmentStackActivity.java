package com.android.custview.fgstack;


import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.android.custview.R;
import com.android.custview.fgstack.fg.Fg01;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.BaseFragment;

public class FragmentStackActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_fragment_stack;
    }

    @Override
    public void initView() {
        BaseFragment.FragmentPack fragmentPack = new BaseFragment.FragmentPack(getSupportFragmentManager(),R.id.fg_holder,FgConstant.NAV_STACK);
        Fg01 fg01 = new Fg01(fragmentPack);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fg_holder, fg01);
        BaseFragment.fragmentMap.put(FgConstant.FG_01, fg01);
        BaseFragment.mIndexMap.put(FgConstant.FG_01, BaseFragment.mCurrentIndex++);
        transaction.commitAllowingStateLoss();

    }

    //模拟外部事件切换Fragment
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add01:
                break;
            case R.id.add02:
                break;
            case R.id.add03:
                break;
            case R.id.pop:
                break;
        }
    }

    @Override
    public void initData() {

    }
    
}
