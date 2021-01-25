package com.android.zp.base;

public class FragmentStackManager {

    private static class SingletonHolder {
        static FragmentStackManager instance = new FragmentStackManager();
    }

    public FragmentStackManager getInstance() {
        return SingletonHolder.instance;
    }



}
