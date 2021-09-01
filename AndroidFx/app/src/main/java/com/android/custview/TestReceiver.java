package com.android.custview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.zp.base.BaseApplicaion;
import com.android.zp.base.KLog;
import com.android.zp.base.toast.PateToast;

public class TestReceiver extends BroadcastReceiver {

    private static final String ACTION_01 = "com.zp.test.action";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(TextUtils.equals(action,ACTION_01)) {
            String value = intent.getStringExtra("key");
            if (!TextUtils.isEmpty(value)) {
                KLog.logI("key : " + value);
                switch (value) {
                    //am broadcast -p com.android.custview -a com.zp.test.action --es key "toast"
                    case "toast":
                        PateToast.getInstance().setContentView(BaseApplicaion.getInstance().getContext()).setTextView("你好").showPateAlertDialog();
                        break;
                    default:
                        break;
                }
            }



        }
    }
}
