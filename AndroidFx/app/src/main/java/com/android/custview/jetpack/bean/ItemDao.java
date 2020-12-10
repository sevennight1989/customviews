package com.android.custview.jetpack.bean;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(List<ItemBean> list);

    @Query("select * from tb_item")
    DataSource.Factory<Integer,ItemBean> queryALl();

    @Query("DELETE FROM tb_item")
    void deleteAll();
}
