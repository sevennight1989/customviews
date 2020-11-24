package com.android.custview.view.framework;

import android.content.Context;

import com.com.android.custview.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardViewController {

    private static CardViewController mInstance;

    private FrameView mViewRoot;
    //存放CardView的实体
    private Map<String, AbstractCardView> mCardViewMap = new HashMap<>();

    private int mCurIndex = 0;

    private Context mContent;
    //用来存放卡片索引的
    private Map<String, Integer> mIndexMap = new HashMap<>();


    public synchronized static CardViewController getInstance() {
        if (mInstance == null) {
            mInstance = new CardViewController();
        }
        return mInstance;
    }

    public void init(FrameView view) {
        if (view == null) {
            KLog.logI("Root View can't be null!");
            return;
        }
        mViewRoot = view;
        mContent = mViewRoot.getContext();
    }

    public void startCardView(CardIntent intent) {
        Class<? extends AbstractCardView> clazz = intent.getCardViewClass();
        String key = clazz.getSimpleName();
        //当前卡片不存在需要添加,注意该框架不支持添加重复的卡片
        if (!isCardViewExist(key)) {
            try {
                AbstractCardView obj = clazz.getDeclaredConstructor(Context.class).newInstance(mContent);
                String lastKey = "";
                if (mCurIndex > 0) {
                    for (Map.Entry<String, Integer> entry : mIndexMap.entrySet()) {
                        int index = entry.getValue();
                        if (index == mCurIndex - 1) {
                            lastKey = entry.getKey();
                            break;
                        }
                    }
                    //添加一个新view会调用之前一个view的onPause函数
                    AbstractCardView lastObj = mCardViewMap.get(lastKey);
                    lastObj.pause();
                }
                mCardViewMap.put(key, obj);
                mIndexMap.put(key, mCurIndex);
                mViewRoot.addView(obj);
                mCurIndex++;
                obj.onCreate(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //需要将当期要显示的卡片上面的卡婆全部移除
            List<AbstractCardView> toRemoveCardList = new ArrayList<>();
            List<String> toRemoveKeyList = new ArrayList<>();
            int currentIndex = mIndexMap.get(key);
            for (Map.Entry<String, Integer> entry : mIndexMap.entrySet()) {
                int index = entry.getValue();
                if (index > currentIndex) {
                    toRemoveKeyList.add(entry.getKey());
                    mIndexMap.remove(entry.getKey());
                }
            }
            for (Map.Entry<String, AbstractCardView> entry : mCardViewMap.entrySet()) {
                String tempKey = entry.getKey();
                if (toRemoveKeyList.contains(tempKey)) {
                    toRemoveCardList.add(entry.getValue());
                    mCardViewMap.remove(entry.getKey());
                }
            }
            AbstractCardView obj = mCardViewMap.get(key);
            if (obj != null) {
                obj.onNewIntent(intent);
            }
            removeCardView(toRemoveCardList);
            mCurIndex = currentIndex;
        }


    }

    private boolean isCardViewExist(String key) {
        return mIndexMap.get(key) != null && mCardViewMap.get(key) != null;


    }

    public void removeCardView(Class<? extends AbstractCardView> clazz) {
        String key = clazz.getSimpleName();
        if (!isCardViewExist(key)) {
            KLog.logI("To removeCard not exist!!!");
            return;
        }
        int curIndex = mIndexMap.get(key);
        String lastKey = "";
        mIndexMap.remove(key);
        for (Map.Entry<String, Integer> entry : mIndexMap.entrySet()) {
            int index = entry.getValue();
            if (index == curIndex - 1) {
                lastKey = entry.getKey();
                break;
            }
        }
        AbstractCardView cardView = mCardViewMap.get(key);
        removeCardViewFinal(cardView);
        mCardViewMap.remove(key);
        AbstractCardView lastCarView = mCardViewMap.get(lastKey);
        lastCarView.restart();


    }

    private void removeCardView(List<AbstractCardView> cardViewList) {
        for (AbstractCardView abstractCardView : cardViewList) {
            removeCardViewFinal(abstractCardView);
        }
    }

    private void removeCardViewFinal(AbstractCardView abstractCardView) {
        abstractCardView.onDestroy();
        mCurIndex--;
        mViewRoot.removeView(abstractCardView);
    }

    public synchronized void release() {
        mCardViewMap.clear();
        mCurIndex = 0;
        mIndexMap.clear();
        mViewRoot.removeAllViews();
    }
}
