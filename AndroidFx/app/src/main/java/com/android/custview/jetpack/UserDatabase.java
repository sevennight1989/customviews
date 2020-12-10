package com.android.custview.jetpack;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.custview.jetpack.bean.ItemBean;
import com.android.custview.jetpack.bean.ItemDao;
import com.android.custview.jetpack.bean.Status;
import com.android.custview.jetpack.bean.StatusDao;
import com.android.custview.jetpack.bean.UserBean;
import com.android.custview.jetpack.bean.UserDao;

@Database(entities = {UserBean.class, Status.class, ItemBean.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract StatusDao getStatusDao();
    public abstract ItemDao getItemDao();

    private static UserDatabase mUserDatabase = null;
    public static UserDatabase getInstance(Context context){
        if(mUserDatabase == null){
            mUserDatabase = Room.databaseBuilder(context,UserDatabase.class,"user_db").build();
        }
        return mUserDatabase;
    }
}
