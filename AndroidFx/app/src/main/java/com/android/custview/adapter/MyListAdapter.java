package com.android.custview.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.custview.R;
import com.android.custview.bean.TestBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyListAdapter extends BaseRVDifferAdapter<TestBean, MyListAdapter.ViewHolder> {

    public MyListAdapter(Context context) {
        super(context, new MyDiffUtilItemCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, List<Object> payLoads) {
        if (payLoads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Bundle bundle = (Bundle) payLoads.get(0);
            holder.mTvName.setText(bundle.getString("KEY_NAME"));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestBean testBean = getItem(position);
        holder.mTvName.setText(testBean.getName());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
