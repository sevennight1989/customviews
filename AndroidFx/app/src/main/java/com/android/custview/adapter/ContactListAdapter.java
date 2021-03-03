package com.android.custview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.custview.R;
import com.android.custview.bean.ContactBean;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private Context mContext;
    private List<ContactBean> mDataList;

    public ContactListAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<ContactBean> mDataList) {
        this.mDataList = mDataList;
    }

    public List<ContactBean> getDataList() {
        return mDataList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_letter_layout, parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactBean contactBean = mDataList.get(position);
        holder.tvLetter.setText("~".equals(contactBean.getNameHeader()) ? "#" : contactBean.getNameHeader());
        holder.tvData.setText(contactBean.getNameChinese());
        if (position == 0) {
            holder.tvLetter.setVisibility(View.VISIBLE);
        } else {
            String currentNameHeader = mDataList.get(position).getNameHeader();
            String nextNameHeader = mDataList.get(position - 1).getNameHeader();
            if (currentNameHeader.equals(nextNameHeader)) {
                holder.tvLetter.setVisibility(View.GONE);
            } else {
                holder.tvLetter.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvLetter;
        TextView tvData;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLetter = itemView.findViewById(R.id.tvLetter);
            tvData = itemView.findViewById(R.id.tvData);
        }
    }
}
