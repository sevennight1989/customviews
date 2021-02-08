package com.android.zp.base;

import android.content.Context;

public class Util_Resource {
    public static int getLayoutIdentifier(Context context, String name) {
        return getResourceId(context, name, "layout");
    }

    public static int getIdIdentifier(Context context, String name) {
        return getResourceId(context, name, "id");
    }

    public static int getDrawableIdentifer(Context context, String name) {
        return getResourceId(context, name, "drawable");
    }

    public static int getMipmapIdentifer(Context context, String name) {
        return getResourceId(context, name, "mipmap");
    }

    public static int getStyleIdentifer(Context context, String name) {
        return getResourceId(context, name, "style");
    }

    public static int getStringIdentifer(Context context, String name) {
        return getResourceId(context, name, "string");
    }

    public static int getAttrIdentifer(Context context, String name) {
        return getResourceId(context, name, "attr");
    }

    public static int getAnimIdentifer(Context context, String name) {
        return getResourceId(context, name, "anim");
    }

    public static int getColorIdentifer(Context context, String name) {
        return getResourceId(context, name, "color");
    }

    public static int getDimenIdentifer(Context context, String name) {
        return getResourceId(context, name, "dimen");
    }


    private static int getResourceId(Context context, String name, String defType) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }

}
