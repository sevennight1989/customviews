package com.android.custview.jetpack.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_item")
public class ItemBean {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    @ColumnInfo
    public String name;
    @ColumnInfo
    int color;

    public ItemBean(String name, int color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}
