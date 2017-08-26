package com.wmk.wb.presenter;

import android.content.Context;

import com.wmk.wb.model.DataManager;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.WbDataStack;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.Statuses;
import com.wmk.wb.model.bean.retjson.WbData;
import com.wmk.wb.model.bean.retjson.topicjson.TopicData;
import com.wmk.wb.model.bean.retjson.topicjson.TopicStatuses;
import com.wmk.wb.utils.ConvertDate;
import com.wmk.wb.utils.TextUtils;
import com.wmk.wb.view.Interface.ITopic;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by wmk on 2017/8/4.
 */

public class TopicAC extends BasePresenter {
    private Context context;
    private ITopic instance;
    private Subscriber<FinalViewData> mSubscribe;
    public TopicAC(ITopic instance) {
        this.instance=instance;

}
    public void getWbData(final Context context, String name, final boolean isNew)
    {
        if(name==null)
            return;
        mSubscribe = new Subscriber<FinalViewData>() {
            List<FinalViewData> data = new ArrayList<>();
            @Override
            public void onCompleted() {
                if (isNew) {
                    WbDataStack.getInstance().getTop().setData(data);
                    instance.scrollToTop();
                }
                else
                    WbDataStack.getInstance().getTop().getData().addAll(data);
                instance.notifyListChange();
                instance.setLoadMore(false);
            }

            @Override
            public void onError(Throwable e) {
                instance.showToast("刷新失败");
                instance.setLoadingFaild();
                instance.setLoadMore(false);
            }
            @Override
            public void onNext(FinalViewData wbData) {
                data.add(wbData);
            }
        };
        if(isNew)
            WbDataStack.getInstance().getTop().setPageCount(1);
        else
            WbDataStack.getInstance().getTop().incPageCount();
        DataManager.getInstance().getTopicData(context,mSubscribe,name,WbDataStack.getInstance().getTop().getPageCount());


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
    public Subscriber<DetialsInfo> getDetialsSubscriber()
    {
        final Subscriber<DetialsInfo> mSubscriber=new Subscriber<DetialsInfo>() {
            @Override
            public void onCompleted() {
                unsubscribe();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DetialsInfo detialsInfo) {
                instance.toActivity(detialsInfo);
            }
        };
        return mSubscriber;
    }
    public  void clearData() {
        WbDataStack.getInstance().getTop().setData(null);
    }
}
