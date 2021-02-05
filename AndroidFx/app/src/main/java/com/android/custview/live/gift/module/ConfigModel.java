package com.android.custview.live.gift.module;

import com.google.gson.annotations.SerializedName;

public class ConfigModel {
    @SerializedName("landscape")
    public Item landscapeItem = null;

    @SerializedName("portrait")
    public Item portraitItem = null;

    public static class Item {
        @SerializedName("path")
        public String path = "";
        @SerializedName("align")
        public int alignMode = 0;
    }
}
