package com.wmk.wb.model.bean;

import android.widget.TextView;

/**
 * Created by wmk on 2017/7/7.
 */

public class LoadingBus {
    TextView loading;
    boolean isPress; //是否为点击通知

    public TextView getLoading() {
        return loading;
    }

    public void setLoading(TextView loading) {
        this.loading = loading;
    }

    public boolean isPress() {
        return isPress;
    }

    public void setPress(boolean press) {
        isPress = press;
    }
}
