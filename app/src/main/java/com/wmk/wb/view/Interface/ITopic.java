package com.wmk.wb.view.Interface;

import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.User;

/**
 * Created by wmk on 2017/8/4.
 */

public interface ITopic {
    void notifyListChange();
    void showToast(String text);
    void setLoadingFaild();
    void toActivity(DetialsInfo detialsInfo);
    void toActivity(Pic_List_Info pic_list_info);
    void setLoadMore(boolean isloading);
    void scrollToTop();
}
