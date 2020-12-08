package com.android.custview.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.custview.R;
import com.android.custview.view.QQListView;
import com.android.custview.utils.KLog;
import com.android.custview.bean.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListActivity extends BaseActivity {

    private QQListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDatas;

    @Override
    public int getLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void initView() {
        mListView = findViewById(R.id.id_listview);
        mDatas = new ArrayList<String>(Arrays.asList("HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",
                "Hibernate", "Spring", "HTML5", "Javascript", "Lucene"));
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setDelButtonClickListener(new QQListView.DelButtonClickListener() {
            @Override
            public void clickHappend(final int position) {
                Toast.makeText(ListActivity.this, position + " : " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                mAdapter.remove(mAdapter.getItem(position));
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, position + " : " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void initData() {
        String type = getIntent().getStringExtra("type");
        if (TextUtils.equals(type, "person")) {
            Person p = getIntent().getParcelableExtra("person");
            if (p != null) {
                KLog.logI(p.toString());
            }
        }
    }
}
