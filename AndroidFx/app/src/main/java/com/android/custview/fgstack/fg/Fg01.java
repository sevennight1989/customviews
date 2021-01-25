package com.android.custview.fgstack.fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.custview.R;
import com.android.zp.base.BaseFragment;
import com.android.zp.base.KLog;
import com.blankj.utilcode.util.ToastUtils;

public class Fg01 extends BaseFragment implements View.OnClickListener {

    private Button start02;
    private Button pop;

    public Fg01(FragmentPack fragmentPack) {
        super(fragmentPack);
    }

    @Override
    protected void onDataReceive(Bundle bundle) {
        if (bundle != null) {
            String value = bundle.getString("key", "");
            KLog.logE("Fg01 onDataReceive: " + value);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg01, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        start02 = view.findViewById(R.id.start02);
        pop = view.findViewById(R.id.pop);
        start02.setOnClickListener(this);
        pop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start02:
                add(Fg02.class);
                break;

            default:
                ToastUtils.showShort("无效的操作");
                break;
        }
    }
    @Override
    public void onDestroy() {
        reset();
        KLog.logE(getClass().getName() + " onDestroy");
        super.onDestroy();
    }

}
