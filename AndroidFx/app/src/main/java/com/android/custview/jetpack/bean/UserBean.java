package com.android.custview.jetpack.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_user")
public class UserBean {
    @PrimaryKey
    public int uid = 0;
    @ColumnInfo
    public String name;
    /**
     * 0 男 1 女
     */
    @ColumnInfo
    public int sex;
    @ColumnInfo
    public int age;
    @ColumnInfo
    public String city;

    public UserBean(int uid, String name, int sex, int age, String city) {
        this.uid = uid;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.city = city;
    }
}
