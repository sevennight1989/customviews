package com.android.custview.jetpack.bean;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface StatusDao {

    @Query("select logged from tb_status where id=0")
    LiveData<Boolean> queryStatus();

    @Insert(entity = Status.class,onConflict = OnConflictStrategy.REPLACE)
    void insert(Status status);

}
