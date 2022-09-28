package com.android.custview.ui;

import com.android.zp.base.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SixTeenTest {

    private static final int STATE_IDLE = 1;
    private static final int STATUS_A = 1 << 1;
    private static final int STATUS_B = 1 << 2;
    private static final int STATUS_C = 1 << 3;

    private static final int DISABLE_BTN_1 = 1 << 4;
    private static final int DISABLE_BTN_2 = 1 << 5;
    private static final int DISABLE_BTN_3 = 1 << 6;

    private static final int MODE_A = STATUS_A | DISABLE_BTN_3;
    private static final int MODE_B = STATUS_B | DISABLE_BTN_1 | DISABLE_BTN_3;
    private static final int MODE_C = STATUS_C | DISABLE_BTN_2 | DISABLE_BTN_3;

    private List<Integer> initModeList() {
        List<Integer> list = new ArrayList<>();
        list.add(MODE_A);
        list.add(MODE_B);
        list.add(MODE_C);
        return list;
    }

    private Map<Integer, String> initToastMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(STATUS_A | DISABLE_BTN_3, "A3");
        map.put(STATUS_B | DISABLE_BTN_1, "B1");
        map.put(STATUS_B | DISABLE_BTN_3, "B3");
        map.put(STATUS_C | DISABLE_BTN_2, "C2");
        map.put(STATUS_C | DISABLE_BTN_3, "C3");
        return map;
    }

    private int currentStatus = STATE_IDLE;

    private boolean checkEnable(int action) {
        List<Integer> list = initModeList();
        Map<Integer, String> toastMap = initToastMap();

        for (int i = 0; i < list.size(); i++) {
            int index = list.get(i);
            if (((index & currentStatus) != 0) && ((index & action) != 0)) {
                KLog.logE("result is false ,toast: " + toastMap.get(currentStatus | action));
                return false;
            }
        }
        KLog.logI("result is true");
        return true;
    }

    public SixTeenTest startTest() {
        currentStatus = STATUS_A;
        KLog.logI("STATUS_A");
        checkEnable(DISABLE_BTN_1);
        checkEnable(DISABLE_BTN_2);
        checkEnable(DISABLE_BTN_3);
        currentStatus = STATUS_B;
        KLog.logI("STATUS_B");
        checkEnable(DISABLE_BTN_1);
        checkEnable(DISABLE_BTN_2);
        checkEnable(DISABLE_BTN_3);
        currentStatus = STATUS_C;
        KLog.logI("STATUS_C");
        checkEnable(DISABLE_BTN_1);
        checkEnable(DISABLE_BTN_2);
        checkEnable(DISABLE_BTN_3);
        return this;
    }

    public void modeTest(){
        int right  = 0x0001;
        int bottom = 0x0002;
        int top    = 0x0008;
        int left   = 0x0016;
        int state = right | bottom | left;
        KLog.logI("state : " + state);
        state = state & ~right;
        KLog.logI("state : " + state);
        state = state & ~top;
        KLog.logI("state : " + state);
        if ((left & state) == left) {
            KLog.logI("Contains left");
        }
    }

}
