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

public class Fg04 extends BaseFragment implements View.OnClickListener {

    private Button start02;
    private Button pop;

    @Override
    protected void onDataReceive(Bundle bundle) {
        if (bundle != null) {
            String value = bundle.getString("key", "");
            KLog.logE("Fg04 onDataReceive: " + value);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg04, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        start02 = view.findViewById(R.id.start02);
        pop = view.findViewById(R.id.pop);
        start02.setOnClickListener(this);
        pop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start02:
                Bundle bundle = new Bundle();
                bundle.putString("key","update to 02");
                add(Fg02.class,bundle);
                break;

            case R.id.pop:
                finish();
                break;
        }
    }
}
