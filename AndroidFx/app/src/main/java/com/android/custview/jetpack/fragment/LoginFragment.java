package com.android.custview.jetpack.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.custview.R;
import com.android.custview.databinding.JetpackLoginMainBinding;
import com.android.custview.utils.KLog;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mViewModel.loginOk.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean ok) {
                KLog.logI("ok: " + ok);
                if(ok !=null && ok){
                    Navigation.findNavController(getActivity(),R.id.host_main).navigateUp();
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        KLog.logI("onCreateView");
        JetpackLoginMainBinding binding = DataBindingUtil.inflate(inflater,R.layout.jetpack_login_main,container, false);
        binding.setVm(mViewModel);
        return binding.getRoot();
    }

}
