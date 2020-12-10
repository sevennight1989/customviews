package com.android.custview.jetpack.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        MainFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container, false);
        binding.setVm(mViewModel);
        return binding.getRoot();
    }

}
