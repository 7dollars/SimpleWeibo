package com.wmk.wb.view.Interface;

import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.Pic_List_Info;

/**
 * Created by wmk on 2017/6/21.
 */

public interface IExplore {
    void setRefresh(boolean refresh,boolean isScrollToTop);
    void setAddress(String addr);
    void notifyListChange();
    void showToast(String text);
    void toActivity(DetialsInfo detialsInfo);
    void toActivity(Pic_List_Info pic_list_info);
    void setLoadingFaild();
    void setLoadMore(boolean loadmore);
}
