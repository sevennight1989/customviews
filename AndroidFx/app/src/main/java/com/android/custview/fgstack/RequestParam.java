package com.android.custview.fgstack;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 处理不同的请求类型
 */
public class RequestParam implements Parcelable {

    public static final String REQUEST_TYPE_POI_LIST = "search_poi_list";
    public static final String REQUEST_TYPE_POI_DETAIL = "search_poi_detail";
    private String requestType;

    private String[] params;

    public RequestParam(String searchType, String[] params) {
        this.requestType = searchType;
        this.params = params;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.requestType);
        dest.writeStringArray(this.params);
    }

    protected RequestParam(Parcel in) {
        this.requestType = in.readString();
        this.params = in.createStringArray();
    }

    public static final Parcelable.Creator<RequestParam> CREATOR = new Parcelable.Creator<RequestParam>() {
        @Override
        public RequestParam createFromParcel(Parcel source) {
            return new RequestParam(source);
        }

        @Override
        public RequestParam[] newArray(int size) {
            return new RequestParam[size];
        }
    };
}
