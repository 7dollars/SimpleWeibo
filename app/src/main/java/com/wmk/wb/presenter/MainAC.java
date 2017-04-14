package com.wmk.wb.presenter;

import com.wmk.wb.model.entity.FinalViewData;
import com.wmk.wb.model.entity.StaticData;

import java.util.List;

/**
 * Created by wmk on 2017/4/4.
 */

public class MainAC implements IMainAC {
    @Override
    public void addDataToMainList(FinalViewData data) {
        StaticData.getInstance().data.add(data);
    }

    @Override
    public void refreshDataToMainList(List<FinalViewData> data) {
        StaticData.getInstance().data=data;
    }

    @Override
    public DataManager getDataDataManager() {
        return DataManager.getInstance();
    }

}
