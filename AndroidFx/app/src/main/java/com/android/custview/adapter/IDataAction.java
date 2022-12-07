package com.android.custview.adapter;

import java.util.List;

public interface IDataAction<T> {
    void setData(List<T> list);

    void addData(List<T> list);

    void removeData(int index);

    void clear();

    List<T> getAdapterData();
}
