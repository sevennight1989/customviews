package com.java.util;

import android.os.Bundle;
import android.view.View;

import com.android.zp.base.BaseActivity;

import android.text.TextUtils;
import com.android.zp.base.KLog;
import com.java.util.chain.ResDownloadHandler;
import com.java.util.chain.ResHandler;
import com.java.util.chain.ResHandlerChain;
import com.java.util.chain.ResMd5CheckHandler;
import com.java.util.chain.ResUnzipHandler;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 线程同步类学习
 */
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
        } else if (id == R.id.start_chain){

            List<ResHandler> handlerList = Stream.of(new ResDownloadHandler(),new ResMd5CheckHandler(),new ResUnzipHandler())
                    .collect(Collectors.toList());
            new ResHandlerChain(handlerList,0).proceed();
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        revertString("abcdef");
        revertString2("abcdefg");
        revertString3("abcdefgh");
    }

    //方向输出字符串
    private void revertString(String str) {
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder(str);
            sb.reverse();
            KLog.logI("revertString : " + sb);
        }
    }

    //方向输出字符串(从中间外两测交换)
    private void revertString2(String str) {
        if (!TextUtils.isEmpty(str)) {
            byte[] val = str.getBytes(StandardCharsets.UTF_8);
            int length = val.length - 1;
            for (int start = (length - 1) >> 1; start >= 0; start--) {
                int end = length - start;
                byte temp = val[start];
                val[start] = val[end];
                val[end] = temp;
            }
            KLog.logI("revertString2 : " + new String(val));
        }
    }

    //方向输出字符串（从两侧向中间交换）
    private void revertString3(String str) {
        if (!TextUtils.isEmpty(str)) {
            char[] result = str.toCharArray();
            int startIndex = 0;
            int endIndex = result.length - 1;
            char temp;
            for (; endIndex > startIndex; endIndex--, startIndex++) {
                temp = result[endIndex];
                result[endIndex] = result[startIndex];
                result[startIndex] = temp;
            }
            KLog.logI("revertString3 : " + new String(result));
        }
    }
}