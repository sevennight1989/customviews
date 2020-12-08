package com.android.custview.jetpack.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.custview.R;
import com.android.custview.databinding.JetpackLoginMainBinding;
import com.android.custview.utils.KLog;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        KLog.logI("onCreateView");
        JetpackLoginMainBinding binding = DataBindingUtil.inflate(inflater,R.layout.jetpack_login_main,container, false);
        binding.setUser(mViewModel);
        return binding.getRoot();
    }

}
