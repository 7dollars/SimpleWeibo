package com.wmk.wb.presenter;

import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.FinalViewData;

/**
 * Created by wmk on 2017/6/7.
 */

public class DetialAC extends BasePresenter{
    public FinalViewData getData(int position)
    {
        return StaticData.getInstance().getData().get(position);
    }
}
