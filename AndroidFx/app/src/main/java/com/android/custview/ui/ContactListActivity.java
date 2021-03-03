package com.android.custview.ui;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.custview.R;
import com.android.custview.adapter.ContactListAdapter;
import com.android.custview.bean.ContactBean;
import com.android.custview.view.LetterView2;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactListActivity extends BaseActivity implements LetterView2.OnLetterChangeListener {

    private LetterView2 mLetterView;

    private RecyclerView lvData;

    private TextView tvLetterHeader;

    private ContactListAdapter mListAdapter;

    private LinearLayoutManager layoutManager;

    private String[] names = {
            "宋江", "卢俊义 ", "吴用 ", "公孙胜 ",
            "关胜 ", "林冲 ", "秦明 ", "呼延灼 ",
            "花荣 ", "柴进", "李应 ", "朱仝 ",
            "鲁智深 ", "武松 ", "董平", "张清 ",
            "杨志 ", "徐宁 ", "索超 ", "戴宗 ",
            "刘唐 ", "李逵 ", "史进 ", "穆弘 ",
            "雷横 ", "李俊 ", "阮小二 ", "张横 ",
            "阮小五 ", "张顺 ", "阮小七 ", "杨雄 ",
            "石秀 ", "解珍 ", "解宝 ", "燕青", "0", ".", "2", "["};

    private List<ContactBean> getData() {

        List<ContactBean> dataBeans = new ArrayList<>();
        for (String name : names) {
            ContactBean dataBean = new ContactBean();
            dataBean.setNameChinese(name);
            dataBeans.add(dataBean);
        }

        Collections.sort(dataBeans, new Comparator<ContactBean>() {
            @Override
            public int compare(ContactBean o1, ContactBean o2) {
                return o1.getNameHeader().compareTo(o2.getNameHeader());
            }
        });
        return dataBeans;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_contact_list;
    }

    @Override
    public void initView() {
        mLetterView = findViewById(R.id.letterView);
        lvData = findViewById(R.id.lvData);
        tvLetterHeader = findViewById(R.id.tvLetterHeader);
    }

    @Override
    public void initData() {
        mLetterView.setOnLetterChangeListener(this);
        lvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLetterView.setTouchIndex(mListAdapter.getDataList().get(layoutManager.findFirstVisibleItemPosition()).getNameHeader());
            }
        });
        layoutManager = new LinearLayoutManager(this);
        lvData.setLayoutManager(layoutManager);
        mListAdapter = new ContactListAdapter(this);
        mListAdapter.setDataList(getData());
        lvData.setAdapter(mListAdapter);

    }

    @Override
    public void onLetterListener(@NotNull String touchIndex) {
        KLog.logI("onLetterListener touchIndex: " + touchIndex);
        tvLetterHeader.setVisibility(View.VISIBLE);
        tvLetterHeader.setText(touchIndex);
        updateListView(touchIndex);
    }

    @Override
    public void onLetterDismissListener() {
        tvLetterHeader.setVisibility(View.GONE);
    }

    private void updateListView(String header) {
        List<ContactBean> list = mListAdapter.getDataList();
        for (int i = 0; i < list.size(); i++) {
            String head = list.get(i).getNameHeader();
            if (TextUtils.equals(head, header)) {
                KLog.logI("updateListView header: " + i);
                layoutManager.scrollToPosition(i);
                return;
            }
        }
    }
}