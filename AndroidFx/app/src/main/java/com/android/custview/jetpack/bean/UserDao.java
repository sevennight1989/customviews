package com.android.custview.jetpack.bean;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Query("select * from tb_user where uid=0")
    LiveData<UserBean> query();

    @Insert(entity = UserBean.class,onConflict = OnConflictStrategy.REPLACE)
    void insert(UserBean userBean);

    @Update
    void update(UserBean userBean);

    @Delete
    void delete(UserBean userBean);

}
