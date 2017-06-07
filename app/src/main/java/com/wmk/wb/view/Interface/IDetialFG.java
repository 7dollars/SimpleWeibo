package com.wmk.wb.view.Interface;

import android.content.Context;

import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.Pic_List_Info;

/**
 * Created by wmk on 2017/6/7.
 */

public interface IDetialFG {
    void toActivity(Pic_List_Info pic_list_info);
    void updateData(boolean flag,String author, String content, String count, String time);
    void updateData(String author, String content, String count,String count_ret,String ret_content,String time);
    void setPic(boolean flag,FinalViewData fdata);
}
