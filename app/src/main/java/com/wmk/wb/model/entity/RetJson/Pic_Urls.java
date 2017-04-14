package com.wmk.wb.model.entity.RetJson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wmk on 2017/4/8.
 */

public class Pic_Urls {
    @SerializedName("thumbnail_pic")
    public String thumbnail_pic;

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }
}
