package com.android.custview.fgstack.fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.custview.R;
import com.android.custview.fgstack.FgConstant;
import com.android.zp.base.BaseFragment;
import com.blankj.utilcode.util.ToastUtils;

public class Fg03 extends BaseFragment implements View.OnClickListener {

    private Button start01;
    private Button pop;

    public Fg03(FragmentPack fragmentPack) {
        super(fragmentPack);
    }

    @Override
    protected void onDataReceive(Bundle bundle) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg03, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        start01 = view.findViewById(R.id.start01);
        pop = view.findViewById(R.id.pop);
        start01.setOnClickListener(this);
        pop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start01:
                Bundle bundle = new Bundle();
                bundle.putString("key","update");
                add(Fg01.class,bundle);
                break;

            case R.id.pop:
                finish();
                break;
        }
    }
}
