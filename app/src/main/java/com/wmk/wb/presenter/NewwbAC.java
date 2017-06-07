package com.wmk.wb.presenter;

import com.wmk.wb.model.DataManager;
import com.wmk.wb.view.Interface.INewWB;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by wmk on 2017/6/7.
 */

public class NewwbAC {
    INewWB instance;
    public NewwbAC(INewWB instance) {
        this.instance=instance;
    }
    public void send(String text)
    {
        if(text!="")
        {
            Subscriber<ResponseBody> mSubscriber=new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                    instance.showToast("发布成功",true);
                }

                @Override
                public void onError(Throwable e) {
                    instance.showToast("发布失败，请稍后再试",false);
                }

                @Override
                public void onNext(ResponseBody s) {
                }
            };
            DataManager.getInstance().createNew(mSubscriber, text);
        }
    }
}
