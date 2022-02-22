package com.java.util;

import android.os.Bundle;
import android.view.View;

import com.android.zp.base.BaseActivity;

public class JavaUtilActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_java_util;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.start_print_thread) {
            PrintNumber.startPrintThread();
        } else if (id == R.id.stop_print_thread) {
            PrintNumber.stopPrintThread();
        } else if (id == R.id.start_print_Semaphore) {
            PrintNumberSemaphore.startPrintNumberSemaphore();
        } else if (id == R.id.stop_print_Semaphore) {
            PrintNumberSemaphore.stopPrintNumberSemaphore();
        } else if (id == R.id.start_print_Lock) {
            PrintNumberLock.startPrintNumberLock();
        } else if (id == R.id.stop_print_Lock) {
            PrintNumberLock.stopPrintNumberLock();
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}