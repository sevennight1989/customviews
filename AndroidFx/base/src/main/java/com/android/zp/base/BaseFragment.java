package com.android.zp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment {

    private FragmentStackManager mFragmentStackManager = FragmentStackManager.getInstance();

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

    protected void add(Class<? extends BaseFragment> clazz){
        add(clazz, null);
    }

    protected void add(Class<? extends BaseFragment> clazz, Bundle bundle){
        mFragmentStackManager.add(clazz,bundle);
    }

    protected void reset(){
        mFragmentStackManager.reset();
    }

    protected void finish() {
        mFragmentStackManager.finish(this);
    }

}
