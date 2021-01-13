package com.android.custview.jetpack.fragment;

import android.os.SystemClock;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.custview.jetpack.UserDatabase;
import com.android.custview.jetpack.bean.Status;
import com.android.custview.jetpack.bean.UserBean;
import com.android.zp.base.KLog;
import com.android.custview.view.InitApplication;
import com.blankj.utilcode.util.ToastUtils;


public class LoginViewModel extends ViewModel {

    public String userName;
    public String password;
    public MutableLiveData<Boolean> loginOk = new MutableLiveData<>();

    public void login(String _userName,String _password){
        KLog.logI("Click login001 " + _userName +"  " + _password);
        KLog.logI("Click login002 " + userName +"  " + password);
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
            ToastUtils.showShort("用户名或密码不能为空");
        }else {
            ToastUtils.showShort("登录成功");
        }
        UserBean userBean = new UserBean(0,"Tom",1,23,"Shanghai");
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserDatabase.getInstance(InitApplication.getContext()).getUserDao().insert(userBean);
                UserDatabase.getInstance(InitApplication.getContext()).getStatusDao().insert(new Status(true));

            }
        }).start();
        SystemClock.sleep(500);
        loginOk.setValue(true);

    }
}
