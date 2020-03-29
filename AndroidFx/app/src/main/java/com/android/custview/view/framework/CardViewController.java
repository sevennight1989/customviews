package com.android.custview.view.framework;

import android.widget.FrameLayout;

import com.com.android.custview.KLog;
import com.com.android.custview.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class CardViewController {

    private static CardViewController mInstance;

    private FrameLayout mViewRoot;

    private Map<String, AbstractCardView> mMap = new HashMap<>();


    public synchronized static CardViewController getInstance() {
        if (mInstance == null) {
            mInstance = new CardViewController();
        }
        return mInstance;
    }

    public void init(FrameLayout view) {
        if (view == null) {
            KLog.logD("Root View can't be null!");
            return;
        }
        mViewRoot = view;
    }

    public void startCardView(Class<? extends AbstractCardView> clazz) {
        try {
            AbstractCardView obj = clazz.getDeclaredConstructor().newInstance();
            mMap.put(StringUtils.toHash(clazz.getSimpleName()), obj);
            mViewRoot.addView(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeCardView(Class<? extends AbstractCardView> clazz) {
        AbstractCardView view = mMap.get(StringUtils.toHash(clazz.getSimpleName()));
        if (mMap.get(StringUtils.toHash(clazz.getSimpleName())) == null) {
            KLog.logD("CardView " + clazz.getSimpleName() + " not exist");
            return;
        }
        mMap.remove(StringUtils.toHash(clazz.getSimpleName()), view);
        mViewRoot.removeView(view);
    }
}
