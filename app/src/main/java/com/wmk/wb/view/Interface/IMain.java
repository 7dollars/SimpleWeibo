package com.wmk.wb.view.Interface;

import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.User;

/**
 * Created by wmk on 2017/6/7.
 */

public interface IMain {
    void setRefresh(boolean refresh,boolean isScrollToTop);
    void notifyListChange();
    void showToast(String text);
    void toActivity(DetialsInfo detialsInfo);
    void toActivity(Pic_List_Info pic_list_info);
    void toActivity(User user);
    void MysetTitle(String title);
}
