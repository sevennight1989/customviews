package com.android.zp.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.GsonUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentStackManager {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public interface Callback {
        //当关闭最后一个Fragment时需要通知Activity
        void onFragmentStackNull();
    }

    private Callback mCallBack;

    public void setCallBack(Callback callBack) {
        mCallBack = callBack;
    }


    public static class FragmentConfig {
        private FragmentManager mFragmentManager;
        private @IdRes
        int id;
        String backStackName;

        public FragmentConfig(FragmentManager mFragmentManager, int id, String backStackName) {
            this.mFragmentManager = mFragmentManager;
            this.id = id;
            this.backStackName = backStackName;
        }

        FragmentManager getFragmentManager() {
            return mFragmentManager;
        }

        void setFragmentManager(FragmentManager mFragmentManager) {
            this.mFragmentManager = mFragmentManager;
        }

        int getId() {
            return id;
        }

        void setId(int id) {
            this.id = id;
        }

        void setBackStackName(String backStackName) {
            this.backStackName = backStackName;
        }

        String getBackStackName() {
            return this.backStackName = backStackName;
        }
    }

    public static Map<String, BaseFragment> fragmentMap = new HashMap<>();

    //用来存放Fragment索引的
    public static Map<String, Integer> mIndexMap = new HashMap<>();
    public static int mCurrentIndex = 0;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    private @IdRes
    int mId;
    String mBackStackName;

    private static class SingletonHolder {
        static FragmentStackManager instance = new FragmentStackManager();
    }

    public static FragmentStackManager getInstance() {
        return SingletonHolder.instance;
    }

    public void setGlobalConfig(FragmentConfig fragmentPack) {
        if (fragmentPack != null) {
            mFragmentManager = fragmentPack.getFragmentManager();
            mId = fragmentPack.getId();
            mBackStackName = fragmentPack.getBackStackName();
        }
    }

    public void add(Class<? extends BaseFragment> clazz) {
        add(clazz, null);
    }

    public void add(Class<? extends BaseFragment> clazz, Bundle bundle) {
        String name = clazz.getSimpleName();
        BaseFragment baseFragment = fragmentMap.get(name);
        if (baseFragment == null) {
            try {
                Constructor con = clazz.getConstructor();
                baseFragment = (BaseFragment) con.newInstance();
                fragmentMap.put(name, baseFragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (baseFragment != null) {
            add(baseFragment, bundle);
        }
    }

    public void add(final BaseFragment targetFragment, final Bundle bundle) {
        if (!targetFragment.isAdded()) {
            mTransaction = mFragmentManager.beginTransaction();
            if (getCurrentFragment() != null) {
                KLog.logE("hide : " + getCurrentFragmentName());
                mTransaction.hide(getCurrentFragment());
            }
            mTransaction.add(mId, targetFragment);
            mTransaction.addToBackStack(mBackStackName);
            mTransaction.commitAllowingStateLoss();
            mIndexMap.put(targetFragment.getClass().getSimpleName(), mCurrentIndex++);
        } else {
            //这里要直接跳转到targetFragment，并且移除targetFragment上面所有的fragment
            int targetIndex = findFragmentIndexByName(targetFragment.getClass().getSimpleName());
            if (targetIndex < 0) {
                KLog.logE("invalid index!!!");
                return;
            }
            int stashSize = mIndexMap.size();
            KLog.logE("target index: " + targetIndex + "  stashSize: " + stashSize);
            List<String> toRemoveList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : mIndexMap.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                if (value > targetIndex) {
                    toRemoveList.add(key);
                }
            }
            removeList(toRemoveList);
            toRemoveList.clear();
        }
        printStashList();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                targetFragment.onDataReceive(bundle);
            }
        });

    }

    public void finish() {
        finish(getCurrentFragment());
    }

    public void finish(BaseFragment baseFragment) {
        if (mFragmentManager != null) {
            remove(baseFragment.getClass().getSimpleName());
            printStashList();
        }
        if (getFragmentCount() == 0) {
            reset();
            if (mCallBack != null) {
                mCallBack.onFragmentStackNull();
            }
        }
    }

    private void removeList(List<String> nameList) {
        for (String name : nameList) {
            remove(name);
        }
    }

    private void remove(String name) {
        KLog.logE("toRemoveKey: " + name);
        mIndexMap.remove(name);
        mCurrentIndex--;
        mFragmentManager.popBackStack();
    }

    private void printStashList() {
        KLog.logE("mCurrentIndex:" + mCurrentIndex + "   stack : " + GsonUtils.toJson(mIndexMap));
    }

    public void reset() {
        KLog.logI("reset");
        mCurrentIndex = 0;
        mIndexMap.clear();
        fragmentMap.clear();
    }

    private int findFragmentIndexByName(String fragmentName) {
        if (mIndexMap.get(fragmentName) != null) {
            return mIndexMap.get(fragmentName);
        }
        return -1;
    }

    public int getFragmentCount(){
        return mIndexMap.size();
    }

    public BaseFragment getCurrentFragment() {
        return fragmentMap.get(getCurrentFragmentName());
    }

    public String getCurrentFragmentName() {
        for (Map.Entry<String, Integer> entry : mIndexMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (value == mCurrentIndex - 1) {
                return key;
            }
        }
        return "default";
    }
}
