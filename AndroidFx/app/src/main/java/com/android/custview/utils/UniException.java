package com.android.custview.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.android.zp.base.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;

public class UniException implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static UniException INSTANCE = new UniException();

    private Context mContext;

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        if (!handleException(e) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException interruptedException) {
                KLog.logE(interruptedException.getMessage());
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private UniException() {
    }

    private static Map<String, String> infos = new HashMap<>();

    public static UniException getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniException();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread(() -> {
            Looper.prepare();
            Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }).start();
        ex.printStackTrace();
        collectDeviceInfo(mContext);
        String logPath = mContext.getExternalCacheDir().getPath() + File.separator + new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.CHINESE).format(new Date()) + ".log";
        KLog.logE("logPath " + logPath);
        File file = new File(logPath);
        if (!file.exists()) {
            try {
                boolean ret = file.createNewFile();
                KLog.logI("Create log file : " + ret);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write((">>>>时间：" +  new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.CHINESE).format(new Date())  + "\r\n").getBytes("utf-8"));
            fos.write(("信息：" + ex.getMessage() + "\r\n").getBytes(StandardCharsets.UTF_8));
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = 0; i < stackTraceElements.length; i++) {
                fos.write(("****StackTrace" + i + "\r\n").getBytes(StandardCharsets.UTF_8));
                fos.write(("行数：" + stackTraceElements[i].getLineNumber() + "\r\n").getBytes(StandardCharsets.UTF_8));
                fos.write(("类名：" + stackTraceElements[i].getClassName() + "\r\n").getBytes(StandardCharsets.UTF_8));
                fos.write(("文件：" + stackTraceElements[i].getFileName() + "\r\n").getBytes(StandardCharsets.UTF_8));
                fos.write(("方法：" + stackTraceElements[i].getMethodName() + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
            }
            fos.write(
                    "--------------------------------\r\n--------------------------------\r\n--------------------------------\r\n"
                            .getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private void collectDeviceInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                infos.put(field.getName(), field.get(null).toString());
                KLog.logE(field.getName() + ":" + field.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
