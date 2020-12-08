package com.android.custview.jetpack.fragment;

import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.android.custview.utils.KLog;
import com.android.custview.view.InitApplication;

public class LoginViewModel extends ViewModel {

    public String userName = "";
    public String password = "";

    public void login(){
        KLog.logI("Click login");
        Toast.makeText(InitApplication.getContext(),"登录成功",Toast.LENGTH_SHORT).show();
    }
}
