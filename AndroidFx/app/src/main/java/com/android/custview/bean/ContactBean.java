package com.android.custview.bean;

import com.android.custview.utils.ContactUtils;

public class ContactBean {
    private String nameChinese;
    private String namePinyin;
    private String nameHeader;

    public String getNameChinese() {
        return nameChinese;
    }

    public void setNameChinese(String nameChinese) {
        this.nameChinese = nameChinese;
        this.namePinyin = ContactUtils.getChineseToPinyin(nameChinese);
        if (ContactUtils.isLetter(namePinyin.substring(0, 1))) {
            this.nameHeader = namePinyin.substring(0, 1);
        } else {
            this.nameHeader = "";
        }
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public String getNameHeader() {
        return nameHeader;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "nameChinese='" + nameChinese + '\'' +
                ", namePinyin='" + namePinyin + '\'' +
                ", nameHeader='" + nameHeader + '\'' +
                '}';
    }


}
