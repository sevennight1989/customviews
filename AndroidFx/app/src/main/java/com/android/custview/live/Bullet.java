package com.android.custview.live;

public class Bullet {

    public static final int BULLET_TYPE_NOTICE = 0;// 弹幕公告

    public static final int BULLET_TYPE_NORMAL = 1; // 普通弹幕

    private String userName = "";
    private String content = "";
    private int msgType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
}
