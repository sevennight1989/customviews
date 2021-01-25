package com.android.zp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.GsonUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseFragment extends Fragment {

    public static Map<String, BaseFragment> fragmentMap = new HashMap<>();

    //用来存放Fragment索引的
    public static Map<String, Integer> mIndexMap = new HashMap<>();
    public static int mCurrentIndex = 0;

    protected abstract void onDataReceive(Bundle bundle);

    @Override
    public void onAttach(@NonNull Context context) {
//        KLog.logE(getClass().getName() + " onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        KLog.logE(getClass().getName() + " onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        KLog.logE(getClass().getName() + " onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        KLog.logE(getClass().getName() + " onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
//        KLog.logE(getClass().getName() + " onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
//        KLog.logE(getClass().getName() + " onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
//        KLog.logE(getClass().getName() + " onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
//        KLog.logE(getClass().getName() + " onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
//        KLog.logE(getClass().getName() + " onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
//        KLog.logE(getClass().getName() + " onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
//        KLog.logE(getClass().getName() + " onDetach");
        super.onDetach();
    }

    public static class FragmentPack {
        private FragmentManager mFragmentManager;
        private @IdRes
        int id;
        String backStackName;

        public FragmentPack(FragmentManager mFragmentManager, int id, String backStackName) {
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

    private FragmentPack mFragmentPack;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private @IdRes
    int mId;
    String mBackStackName;


    protected FragmentPack getFragmentPack() {
        return mFragmentPack;
    }

    public BaseFragment(FragmentPack fragmentPack) {
        if (fragmentPack != null) {
            mFragmentPack = fragmentPack;
            mFragmentManager = fragmentPack.getFragmentManager();
            mId = fragmentPack.getId();
            mBackStackName = fragmentPack.getBackStackName();
        }
    }

    protected void add(Class<? extends BaseFragment> clazz) {
        add(clazz, null);
    }

    protected void add(Class<? extends BaseFragment> clazz, Bundle bundle) {
        String name = clazz.getSimpleName();
        BaseFragment baseFragment = fragmentMap.get(name);
        if (baseFragment == null) {
            try {
                Constructor con = clazz.getConstructor(FragmentPack.class);
                baseFragment = (BaseFragment) con.newInstance(mFragmentPack);
                fragmentMap.put(name, baseFragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (baseFragment != null) {
            add(baseFragment, bundle);
        }
    }


    private void add(BaseFragment targetFragment, Bundle bundle) {
        if (!targetFragment.isAdded()) {
            mTransaction = mFragmentManager.beginTransaction();
            mTransaction.add(mId, targetFragment);
            mTransaction.addToBackStack(mBackStackName);
            mTransaction.commitAllowingStateLoss();
            mIndexMap.put(targetFragment.getClass().getSimpleName(), mCurrentIndex++);
        } else {
            //这里要直接跳转到targetFragment，并且移除targetFragment上面所有的fragment
            int index = findFragmentIndexByName(targetFragment.getClass().getSimpleName());
            if (index < 0) {
                KLog.logE("invalid index!!!");
                return;
            }
            int stashSize = mIndexMap.size();
            KLog.logE("index: " + index + "  stashSize: " + stashSize);
            List<String> toRemoveList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : mIndexMap.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                if (value > index) {
                    toRemoveList.add(key);
                }
            }
            removeList(toRemoveList);
            toRemoveList.clear();
        }
        printStashList();
        targetFragment.onDataReceive(bundle);
    }

    private void removeList(List<String> nameList){
        for (String name : nameList) {
            mIndexMap.remove(name);
            mCurrentIndex--;
            mFragmentManager.popBackStack();
        }
    }

    protected void finish() {
        if (mFragmentManager != null) {
            String toRemoveKey = getClass().getSimpleName();
            KLog.logE("toRemoveKey: " + toRemoveKey);
            mIndexMap.remove(toRemoveKey);
            mCurrentIndex--;
            mFragmentManager.popBackStack();
            printStashList();
        }
    }

    private void printStashList() {
        KLog.logE("mCurrentIndex:" + mCurrentIndex + "   stack : " + GsonUtils.toJson(mIndexMap));
    }

    protected void reset(){
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
}
