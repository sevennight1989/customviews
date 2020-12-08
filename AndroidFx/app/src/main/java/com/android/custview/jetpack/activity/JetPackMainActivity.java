package com.android.custview.jetpack.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.android.custview.R;
import com.android.custview.databinding.ActivityJetPackMainBinding;
import com.android.custview.jetpack.UserDatabase;
import com.android.custview.utils.KLog;

public class JetPackMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
                actionBar.hide();
        }
        ActivityJetPackMainBinding bind = DataBindingUtil.setContentView(this,R.layout.activity_jet_pack_main);

/*        UserDatabase.getInstance(this).getUserDao().query().observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {

            }
        });*/
        UserDatabase.getInstance(this).getStatusDao().queryStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean logged) {
                KLog.logI("logged: " + logged);
                if(logged == null || !logged){
                    Navigation.findNavController(JetPackMainActivity.this,R.id.host_main).navigate(R.id.fg_login);
                }
            }
        });
    }
}
