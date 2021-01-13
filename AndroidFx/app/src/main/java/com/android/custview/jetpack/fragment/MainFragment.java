package com.android.custview.jetpack.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.custview.R;
import com.android.custview.databinding.MainFragmentBinding;
import com.android.custview.jetpack.bean.ItemBean;
import com.android.zp.base.KLog;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        KLog.logE("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.logE("onCreate");
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.init();
        mViewModel.liveList.observe(this, new Observer<PagedList<ItemBean>>() {
            @Override
            public void onChanged(PagedList<ItemBean> itemBeans) {
                mViewModel.myAdapter.submitList(itemBeans);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        KLog.logE("onCreateView");
        MainFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container, false);
        binding.setVm(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        KLog.logE("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        KLog.logE("onResume");
        super.onResume();
        getParentFragmentManager().beginTransaction().setMaxLifecycle(this, Lifecycle.State.CREATED).commit();
    }

    @Override
    public void onPause() {
        KLog.logE("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        KLog.logE("onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        KLog.logE("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        KLog.logE("onDestroyView");
        super.onDestroyView();
    }
}
