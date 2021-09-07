package com.android.custview.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.custview.R;
import com.android.zp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MultiItemActivity extends BaseActivity {
    private RecyclerView mRv;
    private MultiAdapter multiAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_multi_item;
    }

    @Override
    public void initView() {
        mRv = findViewById(R.id.rc);
        mRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        multiAdapter = new MultiAdapter(this);
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dataList.add("RecycleView Item : " + i);
        }
        multiAdapter.setDataList(dataList);
        mRv.setAdapter(multiAdapter);
    }

    public static class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {
        private Context mContext;
        private List<String> dataList = new ArrayList<>();

        public MultiAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setDataList(List<String> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.multi_item, null);
            return new MultiViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
            holder.itemTv.setText(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        static class MultiViewHolder extends RecyclerView.ViewHolder {
            TextView itemTv;

            public MultiViewHolder(@NonNull View itemView) {
                super(itemView);
                itemTv = itemView.findViewById(R.id.item_text);
            }
        }
    }

}
