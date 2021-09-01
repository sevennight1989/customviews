package com.android.custview.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 防止重复点击
 */
public class ObjectOperateLock {
    private static final long DEFAULT_PERIOD = 200L;
    private long period = 0L;
    private long lastOperateTime = 0;
    private List<Integer> operateList = new LinkedList<>();
    private HashMap<Integer, Long> timeHashMap = new HashMap<>();

    public ObjectOperateLock() {
        period = DEFAULT_PERIOD;
        Object obj = new Object();
    }

    public ObjectOperateLock(long minimumPeriod) {
        period = minimumPeriod;
    }

    public boolean doing(Object obj) {
        return doing(obj, period);
    }

    public boolean doing(Object obj, long minimumPeriod) {
        int code = obj.hashCode();
        boolean doing = false;
        int wk = 0;
        Iterator<Integer> iterator = operateList.iterator();
        while (iterator.hasNext()) {
            int w = iterator.next();
            if (w == 0) {
                iterator.remove();
                timeHashMap.remove(w);
            } else if (w == code) {
                wk = w;
            }
        }
        if (wk == 0) {
            wk = code;
            operateList.add(wk);
            timeHashMap.put(wk, System.currentTimeMillis());
            doing = false;
        } else {
            long cur = System.currentTimeMillis();
            lastOperateTime = timeHashMap.get(wk);
            if (cur - lastOperateTime > minimumPeriod) {
                doing = true;
                lastOperateTime = cur;
                timeHashMap.put(wk, lastOperateTime);
            }
        }
        return doing;
    }
}
