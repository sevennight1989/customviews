package com.android.custview.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRVDifferAdapter<T, VH extends RecyclerView.ViewHolder> extends ListAdapter<T, VH> implements IDataAction<T> {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas = new ArrayList<>();

    protected BaseRVDifferAdapter(Context context, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void setData(List<T> list) {
        mDatas.clear();
        mDatas.addAll(list);
        List<T> mList = new ArrayList<>();
        mList.addAll(mDatas);
        submitList(mList);
    }

    @Override
    public void addData(List<T> list) {
        mDatas.addAll(list);
        List<T> mList = new ArrayList<>();
        mList.addAll(mDatas);
        submitList(mList);
    }

    @Override
    public void removeData(int index) {
        mDatas.remove(index);
        List<T> mList = new ArrayList<>();
        mList.addAll(mDatas);
        submitList(mList);
    }

    @Override
    public void clear() {
        mDatas.clear();
        submitList(null);
    }

    @Override
    public List<T> getAdapterData() {
        return mDatas;
    }
}
