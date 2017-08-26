package com.wmk.wb.presenter;

import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.WbDataStack;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.retjson.WbData;

/**
 * Created by wmk on 2017/6/7.
 */

public class DetialAC extends BasePresenter{
    public FinalViewData getData(int position)
    {
        return WbDataStack.getInstance().getTop().getData().get(position);
    }
}
