package com.wmk.wb.presenter;

import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.view.Interface.IDetialFG;

import rx.Subscriber;

/**
 * Created by wmk on 2017/6/7.
 */

public class DetialFG {
    private IDetialFG instance;
    public DetialFG(IDetialFG instance) {
        this.instance=instance;
    }

    public Subscriber<Pic_List_Info> getPicSubscriber()
    {
        final Subscriber<Pic_List_Info> mSubscriber=new Subscriber<Pic_List_Info>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Pic_List_Info pic_list_info) {
                instance.toActivity(pic_list_info);
            }
        };

        return mSubscriber;
    }
    public void setData(int position, boolean isRet, boolean hasChild)
    {
        FinalViewData fdata= StaticData.getInstance().getData().get(position);

        if(isRet) {
            instance.updateData(false,fdata.getRet_name(),fdata.getRet_text(),fdata.getReposts_count_ret() + "转发 | " + fdata.getComments_count_ret() + "回复",fdata.getRet_time());
            instance.setPic(false,fdata);
        }
        else if(hasChild)
        {
            instance.updateData(fdata.getName(),fdata.getText(),fdata.getReposts_count() + "转发 | " + fdata.getComments_count() + "回复",fdata.getReposts_count_ret() + "转发 | " + fdata.getComments_count_ret() + "回复","@"+fdata.getRet_name()+":"+fdata.getRet_text(),fdata.getTime());
            instance.setPic(true,fdata);
        }
        else
        {
            instance.updateData(true,fdata.getName(),fdata.getText(),fdata.getReposts_count() + "转发 | " + fdata.getComments_count() + "回复",fdata.getTime());
            instance.setPic(true,fdata);

        }

    }
}
