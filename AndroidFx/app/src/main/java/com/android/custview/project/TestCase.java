package com.android.custview.project;


import com.android.zp.base.KLog;

import org.json.JSONException;
import org.json.JSONObject;

public class TestCase {

    private static class SingletonHolder {
        static TestCase instance = new TestCase();
    }

    public static TestCase getInstance() {
        return TestCase.SingletonHolder.instance;
    }

    public void sendAccStatus(boolean isAccOn) {
        KLog.logI("sendAccStatus isAccOn: " + isAccOn);
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("command", ConstDef.CMD_ACC_STATUS);
            JSONObject jsonData = new JSONObject();
            jsonData.put("acc", isAccOn ? "on" : "off");
            jsonParams.put("params", jsonData);
            sendRequest("external_command", jsonParams, ConstDef.REQ_ID_ACC_STATUS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(String type, JSONObject jsonParams, String requestId) {
        KLog.logI("sendRequest func : " + type + " params: " + jsonParams.toString() + " requestId: " + requestId);
    }
}
