package com.android.custview.view;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.android.custview.utils.KLog;

public class AppManifestUtil {

    public interface ManifestName {
        String LogDir = "com.zp.lr.logdir";
    }

    public static class ManifestValue {
        public static String VALUE_LogDir = "";
    }

    public static String getAndroidManifestStringValueByName(Context context, String name) {
        try {
            ApplicationInfo ai = null;
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String value = bundle.getString(name);
            KLog.logI("name:" + name + " value:" + value);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            KLog.logI("Exception:" + e.getMessage());
        }
        return null;
    }

    public static boolean getAndroidManifestBooleanValueByName(Context context, String name) {
        try {
            ApplicationInfo ai = null;
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            boolean value = bundle.getBoolean(name);
            KLog.logI("name:" + name + " value:" + value);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            KLog.logI("Exception:" + e.getMessage());
        }
        return false;
    }

    public static int getAndroidManifestIntValueByName(Context context, String name) {
        try {
            ApplicationInfo ai = null;
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            int value = bundle.getInt(name);
            KLog.logI("name:" + name + " value:" + value);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            KLog.logI("Exception:" + e.getMessage());
        }
        return -1;
    }
}
