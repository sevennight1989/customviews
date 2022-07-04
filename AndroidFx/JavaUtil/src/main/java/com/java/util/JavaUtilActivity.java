package com.java.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.android.zp.base.BaseActivity;

import android.text.TextUtils;
import android.widget.LinearLayout;

import com.android.zp.base.KLog;
import com.java.util.chain.ResDownloadHandler;
import com.java.util.chain.ResHandler;
import com.java.util.chain.ResHandlerChain;
import com.java.util.chain.ResMd5CheckHandler;
import com.java.util.chain.ResUnzipHandler;
import com.java.util.chain2.InterceptChain;
import com.java.util.chain2.InterceptFillInfo;
import com.java.util.chain2.InterceptMemberApprove;
import com.java.util.chain2.InterceptNewMember;
import com.java.util.chain2.InterceptSkill;
import com.java.util.chain2.JobInterceptBean;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.annotation.RequiresApi;

/**
 * Java 线程同步类学习
 */
public class JavaUtilActivity extends BaseActivity {
    private LinearLayout ll;
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
        } else if (id == R.id.start_chain2){
            JobInterceptBean jobInterceptBean = new JobInterceptBean();
            InterceptNewMember interceptNewMember = new InterceptNewMember(jobInterceptBean);
            InterceptChain chain = InterceptChain.create(4)
                    .attach(this)
                    .addInterceptor(interceptNewMember)
                    .addInterceptor(new InterceptFillInfo(jobInterceptBean))
                    .addInterceptor(new InterceptMemberApprove(jobInterceptBean))
                    .addInterceptor(new InterceptSkill(jobInterceptBean))
                    .build();
            chain.process();
        }
    }
    private final static String TEST_PRODUCT_ID = "4806038";
    private final static String TEST_DEVICE_ID = "PateoTest008";
    private final static String TEST_SIGNATURE = "your_signature";

    private void generateSignature(){


    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void initView() {
        ll = findViewById(R.id.ll);
        ll.setBackground(getBackgroundDrawable());
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private Drawable getBackgroundDrawable() {
        int[] colors = {Color.RED, Color.GREEN, };
//        int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.WHITE, Color.BLACK};
//        int[] colors2 = {0XB2366FBB,0xB2285CA4,0xB22E67B4,0xB23D7DC9,0xB264B0F4};
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(150);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColors(colors,new float[]{0.8f,0.2f});
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        return gradientDrawable;
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