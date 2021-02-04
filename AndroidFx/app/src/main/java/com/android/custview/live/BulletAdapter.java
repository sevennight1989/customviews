package com.android.custview.live;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.custview.R;

import java.util.ArrayList;
import java.util.List;

public class BulletAdapter extends RecyclerView.Adapter<BulletAdapter.ViewHolder> {

    private List<Bullet> listData = new ArrayList<>();

    public List<Bullet> getListData() {
        return listData;
    }

    private Context mContext;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext = recyclerView.getContext();
    }

    public void addData(Bullet bullet) {
        listData.add(bullet);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_bullet, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bullet bullet = listData.get(position);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FFC323"));
        if (bullet.getMsgType() == Bullet.BULLET_TYPE_NOTICE) {
            builder.append(bullet.getContent());
            builder.setSpan(colorSpan, 0, bullet.getContent().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            String userName = bullet.getUserName() + " : ";
            builder.append(userName);
            builder.append(bullet.getContent());
            builder.setSpan(colorSpan, 0, userName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.text.setText(builder);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_bullet);
        }
    }
}
