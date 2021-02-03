package com.android.zp.base;

import android.content.Context;
import android.os.Bundle;

public interface IStackManager {

    void add(Context context,Class<? extends BaseFragment> clazz);
    void add(Context context,Class<? extends BaseFragment> clazz, Bundle bundle);
    void finish(Context context);
    void finish(Context context,BaseFragment baseFragment);
    void reset(Context context);
    int getFragmentCount(Context context);
    BaseFragment getCurrentFragment(Context context);
    String getCurrentFragmentName(Context context);

}
