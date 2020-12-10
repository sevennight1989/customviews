package com.android.custview.jetpack.fragment;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;

import com.android.custview.jetpack.UserDatabase;
import com.android.custview.jetpack.adapter.MyAdapter;
import com.android.custview.jetpack.bean.ItemBean;
import com.android.custview.jetpack.bean.ItemDao;
import com.android.custview.view.InitApplication;

public class MainViewModel extends ViewModel {

    private static DiffUtil.ItemCallback<ItemBean>  diffCallBack = new DiffUtil.ItemCallback<ItemBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull ItemBean oldItem, @NonNull ItemBean newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ItemBean oldItem, @NonNull ItemBean newItem) {
            return TextUtils.equals(oldItem.name,newItem.name);
        }
    };
    public MyAdapter myAdapter = new MyAdapter(diffCallBack);

    public LiveData<PagedList<ItemBean>> liveList = null;

    public void init(){
       ItemDao itemDao = UserDatabase.getInstance(InitApplication.getContext()).getItemDao();
        PagedList.Config.Builder builder = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(100)
                .setPageSize(50)
                .setEnablePlaceholders(false)
                .setPrefetchDistance(10);
        liveList = new LivePagedListBuilder<Integer,ItemBean>(itemDao.queryALl(),builder.build()
        ).build();
    }

}
