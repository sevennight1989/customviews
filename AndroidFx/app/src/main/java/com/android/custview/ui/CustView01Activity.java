package com.android.custview.ui;


import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.android.custview.R;
import com.android.custview.view.ExpandLinearLayout;
import com.android.custview.view.TagTextView;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;

import java.util.ArrayList;
import java.util.List;

public class CustView01Activity extends BaseActivity {

    private ExpandLinearLayout expand;
    private TextView tv_tip;
    private TagTextView tv_tag1;
    private TagTextView tv_tag2;
    private TagTextView tv_tag3;

    @Override
    public int getLayout() {
        return R.layout.activity_cust_view01;
    }

    @Override
    public void initView() {
        expand = findViewById(R.id.expand);
        tv_tip = findViewById(R.id.tv_tip);
        tv_tag1 = findViewById(R.id.tv_tag1);
        tv_tag2 = findViewById(R.id.tv_tag2);
        tv_tag3 = findViewById(R.id.tv_tag3);
        tv_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean toggle = expand.toggle();
                KLog.logI("toggle: " + toggle);
                tv_tip.setText(toggle ? "收起" : "展开");
            }
        });
        List<String> list1 = new ArrayList<>();
        list1.add("标签");

        tv_tag1.setTagAndText(
                list1,
                Color.parseColor("#F44336"),
                Color.parseColor("#FFCDD2"),
                getString(R.string.app_name)
        );

        List<String> list2 = new ArrayList<String>();
        list2.add("会员价");
        list2.add("促销");
        list2.add("抢购");

        tv_tag2.setTagAndText(
                list2,
                Color.parseColor("#1976D2"),
                Color.parseColor("#BBDEFB"),
                getString(R.string.test_string)
        );

        List<String> list3 = new ArrayList<>();
        list3.add("标签");
        tv_tag3.setTagAndText(
                list3,
                Color.parseColor("#388E3C"),
                Color.parseColor("#C8E6C9"),
                getString(R.string.test_string),
                6,
                8,
                Color.parseColor("#4CAF50")
        );

    }

    @Override
    public void initData() {
    }
}
