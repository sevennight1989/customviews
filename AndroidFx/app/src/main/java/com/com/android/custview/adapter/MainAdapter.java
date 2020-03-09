package com.com.android.custview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.custview.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    public interface OnClickListener {
        void onClick(int pos);
    }

    private OnClickListener mOnClickListener;

    private String[] mData;

    private Context mContext;

    public MainAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnClickListener(OnClickListener clickListener) {
        mOnClickListener = clickListener;
    }

    public void setData(String[] data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.main_item, parent,false);
        final MainViewHolder mainViewHolder = new MainViewHolder(contentView);
        mainViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onClick(mainViewHolder.getAdapterPosition());
            }
        });
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.itemText.setText(mData[position]);

    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;

        public MainViewHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.item_text);
        }
    }
}
