package com.android.custview.ui;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.custview.R;
import com.android.custview.layout.LinearLayoutManagerExtend;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Learn05Activity extends BaseActivity {
    private RecyclerView rl1;
    private RecyclerView head_rv;
    private MyAdapter adapter;
    private MyAdapter2 adapter2;
    private LinearLayoutManagerExtend llm;

    @Override
    public int getLayout() {
        return R.layout.activity_learn05;
    }

    @Override
    public void initView() {
        llm = new LinearLayoutManagerExtend(this);
        llm.setSmoothScrollbarEnabled(true);
        llm.setAutoMeasureEnabled(true);
        rl1 = findViewById(R.id.rl1);
        adapter = new MyAdapter();
        rl1.setLayoutManager(llm);
        rl1.setAdapter(adapter);
        rl1.setHasFixedSize(true);
        rl1.setNestedScrollingEnabled(false);
        View headView = LayoutInflater.from(this).inflate(R.layout.attach_layout, rl1, false);
        head_rv = headView.findViewById(R.id.head_rv);
        head_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new MyAdapter2();
        head_rv.setAdapter(adapter2);
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        KLog.logI("Learn05Activity dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        KLog.logI("Learn05Activity onTouchEvent");
        return true;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        private String[] datas = {"A", "B", "C", "D", "E", "F"};

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, null);

            return new MyHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.tv.setText(datas[position]);
        }

        @Override
        public int getItemCount() {
            return datas.length;
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            public TextView tv;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.item_text);

            }
        }
    }

    private class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyHolder> {

        private String[] datas = {"A", "B", "C"};

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, null);

            return new MyHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.tv.setText(datas[position]);
        }

        @Override
        public int getItemCount() {
            return datas.length;
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            public TextView tv;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.item_text);

            }
        }
    }
}