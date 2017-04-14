package com.wmk.wb.model.entity;

/**
 * Created by wmk on 2017/4/12.
 */

public class DetialsInfo {
    public int position;    //点击的position
    public boolean isRet;   //点击的是否是转发数据
    public boolean hasChild;//是否包含转发数据（前提点击的不是转发数据）

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isRet() {
        return isRet;
    }

    public void setRet(boolean ret) {
        isRet = ret;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}
