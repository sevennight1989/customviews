package com.android.zp.base;

import android.content.Context;
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

public class FragmentStackManager implements IStackManager {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void add(Context context, Class<? extends BaseFragment> clazz) {
        prepareContext(context);
        add(clazz);
    }

    @Override
    public void add(Context context, Class<? extends BaseFragment> clazz, Bundle bundle) {
        prepareContext(context);
        add(clazz, bundle);
    }

    @Override
    public void finish(Context context) {
        prepareContext(context);
        finish();
    }

    @Override
    public void finish(Context context, BaseFragment baseFragment) {
        prepareContext(context);
        finish(baseFragment);
    }

    @Override
    public void reset(Context context) {
        prepareContext(context);
        reset();
    }

    @Override
    public int getFragmentCount(Context context) {
        prepareContext(context);
        getFragmentCount();
        return 0;
    }

    @Override
    public BaseFragment getCurrentFragment(Context context) {
        prepareContext(context);
        return getCurrentFragment();
    }

    @Override
    public String getCurrentFragmentName(Context context) {
        prepareContext(context);
        return getCurrentFragmentName();
    }

    public interface Callback {
        //当关闭最后一个Fragment时需要通知Activity
        void onFragmentStackNull();
    }


    public void setCallBack(Callback callBack) {
        getDataWrapper().callback = callBack;
    }


    public static class FragmentConfig {
        private FragmentManager mFragmentManager;
        private @IdRes
        int id;

        public FragmentConfig(FragmentManager mFragmentManager, int id) {
            this.mFragmentManager = mFragmentManager;
            this.id = id;
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

    }

    Map<String, DataWrapper> mDataWrapperMap = new HashMap<>();
    private Context mContext;

    public static class DataWrapper {
        Callback callback;
        FragmentManager mFragmentManager;
        Map<String, BaseFragment> fragmentMap = new HashMap<>();
        Map<String, Integer> mIndexMap = new HashMap<>();
        int mCurrentIndex = 0;
        int mId;

        public FragmentManager getFragmentManager() {
            return mFragmentManager;
        }

        public DataWrapper setFragmentManager(FragmentManager mFragmentManager) {
            this.mFragmentManager = mFragmentManager;
            return this;
        }

        public FragmentTransaction getTransaction() {
            return mFragmentManager.beginTransaction();
        }

        public Map<String, BaseFragment> getFragmentMap() {
            return fragmentMap;
        }

        public DataWrapper setFragmentMap(Map<String, BaseFragment> fragmentMap) {
            this.fragmentMap = fragmentMap;
            return this;
        }

        public Map<String, Integer> getIndexMap() {
            return mIndexMap;
        }

        public DataWrapper setIndexMap(Map<String, Integer> mIndexMap) {
            this.mIndexMap = mIndexMap;
            return this;
        }

        public int getCurrentIndex() {
            return mCurrentIndex;
        }

        public DataWrapper setCurrentIndex(int mCurrentIndex) {
            this.mCurrentIndex = mCurrentIndex;
            return this;
        }

        public int getId() {
            return mId;
        }

        public DataWrapper setId(int mId) {
            this.mId = mId;
            return this;
        }
    }

    private static class SingletonHolder {
        static FragmentStackManager instance = new FragmentStackManager();
    }

    public static FragmentStackManager getInstance() {
        return SingletonHolder.instance;
    }

    private String getContextName() {
        return mContext.getClass().getSimpleName();
    }

    public void setGlobalConfig(Context context, FragmentConfig fragmentPack) {
        prepareContext(context);
        if (fragmentPack != null) {
            DataWrapper dataWrapper = new DataWrapper().setFragmentManager(fragmentPack.getFragmentManager())
                    .setId(fragmentPack.getId())
                    .setCurrentIndex(0);
            mDataWrapperMap.put(getContextName(), dataWrapper);
        }
    }

    private void prepareContext(Context context) {
        if (context != null) {
            mContext = context;
            KLog.logI("prepareContext: " + context.getClass().getSimpleName());
        }
    }

    public void add(Class<? extends BaseFragment> clazz) {
        add(clazz, null);
    }

    public void add(Class<? extends BaseFragment> clazz, Bundle bundle) {
        String name = clazz.getSimpleName();
        BaseFragment baseFragment = getDataWrapper().fragmentMap.get(name);
        if (baseFragment == null) {
            try {
                Constructor con = clazz.getConstructor();
                baseFragment = (BaseFragment) con.newInstance();
                getDataWrapper().fragmentMap.put(name, baseFragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (baseFragment != null) {
            add(baseFragment, bundle);
        }
    }

    private DataWrapper getDataWrapper() {
        return mDataWrapperMap.get(getContextName());
    }

    public void add(final BaseFragment targetFragment, final Bundle bundle) {
        if (!targetFragment.isAdded()) {
            FragmentTransaction mTransaction = getDataWrapper().getTransaction();
            if (getCurrentFragment() != null) {
                KLog.logE("hide : " + getCurrentFragmentName());
                mTransaction.hide(getCurrentFragment());
            }
            mTransaction.add(getDataWrapper().mId, targetFragment);
            mTransaction.addToBackStack(getContextName());
            mTransaction.commitAllowingStateLoss();
            int mCurrentIndex = getDataWrapper().mCurrentIndex;
            getDataWrapper().setCurrentIndex(++mCurrentIndex);
            getDataWrapper().mIndexMap.put(targetFragment.getClass().getSimpleName(), mCurrentIndex);
        } else {
            //这里要直接跳转到targetFragment，并且移除targetFragment上面所有的fragment
            int targetIndex = findFragmentIndexByName(targetFragment.getClass().getSimpleName());
            if (targetIndex < 0) {
                KLog.logE("invalid index!!!");
                return;
            }
            int stashSize = getDataWrapper().mIndexMap.size();
            KLog.logE(getContextName() + "  target index: " + targetIndex + "  stashSize: " + stashSize);
            List<String> toRemoveList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : getDataWrapper().mIndexMap.entrySet()) {
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
        if (getDataWrapper().mFragmentManager != null) {
            remove(baseFragment.getClass().getSimpleName());
            printStashList();
        }
        if (getFragmentCount() == 0) {
            reset();
            Callback callback = getDataWrapper().callback;
            if (callback != null) {
                callback.onFragmentStackNull();
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
        getDataWrapper().mIndexMap.remove(name);
        int mCurrentIndex = getDataWrapper().mCurrentIndex;
        mCurrentIndex--;
        getDataWrapper().setCurrentIndex(mCurrentIndex);
        getDataWrapper().mFragmentManager.popBackStack();
    }

    private void printStashList() {
        KLog.logE(getContextName() + "  mCurrentIndex:" + getDataWrapper().mCurrentIndex + "   stack : " + GsonUtils.toJson(getDataWrapper().mIndexMap));
    }

    public void reset() {
        KLog.logI("reset");
        getDataWrapper().setCurrentIndex(0);
        getDataWrapper().mIndexMap.clear();
        getDataWrapper().fragmentMap.clear();
    }

    private int findFragmentIndexByName(String fragmentName) {
        if (getDataWrapper().mIndexMap.get(fragmentName) != null) {
            return getDataWrapper().mIndexMap.get(fragmentName);
        }
        return -1;
    }

    public int getFragmentCount() {
        return getDataWrapper().mIndexMap.size();
    }

    public BaseFragment getCurrentFragment() {
        return getDataWrapper().fragmentMap.get(getCurrentFragmentName());
    }

    public String getCurrentFragmentName() {
        KLog.logI("getCurrentFragmentName: " + GsonUtils.toJson(getDataWrapper().mIndexMap));
        for (Map.Entry<String, Integer> entry : getDataWrapper().mIndexMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (value == getDataWrapper().mCurrentIndex) {
                return key;
            }
        }
        return "default";
    }
}
