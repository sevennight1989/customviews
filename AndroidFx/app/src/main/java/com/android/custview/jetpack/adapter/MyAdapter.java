package com.android.custview.jetpack.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.custview.databinding.ItemRvMainBinding;
import com.android.custview.jetpack.bean.ItemBean;
import com.blankj.utilcode.util.ToastUtils;

public class MyAdapter extends PagedListAdapter<ItemBean,MyAdapter.ListViewHolder> {

    public MyAdapter(DiffUtil.ItemCallback<ItemBean> diffCallBack) {
        super(diffCallBack);
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvMainBinding binding = ItemRvMainBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ListViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        private  ItemRvMainBinding binding;
        public ListViewHolder(@NonNull View itemView, ItemRvMainBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        public void bind(ItemBean itemBean){
            binding.setBean(itemBean);
            binding.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort("点击了 item " + itemBean);
                }
            });
        }
    }

}
