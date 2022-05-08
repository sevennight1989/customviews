package com.android.custview.ui;



import com.android.custview.R;
import com.android.custview.learn.MaterialEditText;
import com.android.zp.base.BaseActivity;

public class Learn03Activity extends BaseActivity {
    private MaterialEditText materialEditText;

    @Override
    public int getLayout() {
        return R.layout.activity_learn03;
    }

    @Override
    public void initView() {
        materialEditText = findViewById(R.id.material_et);
//        materialEditText.setUseFloatingLabel(true);
//        materialEditText.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                materialEditText.setUseFloatingLabel(false);
//            }
//        },3000);
    }

    @Override
    public void initData() {

    }
}