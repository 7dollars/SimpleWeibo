package com.wmk.wb.presenter;

import com.wmk.wb.model.entity.FinalViewData;

import java.util.List;

/**
 * Created by wmk on 2017/4/4.
 */

public interface IMainAC {
    void addDataToMainList(FinalViewData data);
    void refreshDataToMainList(List<FinalViewData> data);
    DataManager getDataDataManager();

}
