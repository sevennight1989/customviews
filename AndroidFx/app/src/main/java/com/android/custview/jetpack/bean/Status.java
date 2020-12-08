package com.android.custview.jetpack.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_status")
public class Status {
    @PrimaryKey(autoGenerate = false)
    public int id;
    @ColumnInfo
    public boolean logged;
}
