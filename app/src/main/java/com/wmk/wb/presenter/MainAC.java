package com.wmk.wb.presenter;

import android.content.Context;

import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.DataManager;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.Access_token;
import com.wmk.wb.model.bean.retjson.WbData;
import com.wmk.wb.utils.ConvertDate;
import com.wmk.wb.utils.SpUtil;
import com.wmk.wb.view.Interface.IMain;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by wmk on 2017/4/4.
 */

public class MainAC{
    private IMain instance;
    public MainAC(IMain instance) {
        this.instance=instance;
    }

    public void getWbData(final long max_id)//获取微博数据，max_id:数据起始id，0为从最新开始获取
    {
        Subscriber<WbData> mSubscribe;
        mSubscribe=new Subscriber<WbData>() {

            @Override
            public void onCompleted() {
                if(max_id==0) {
                    instance.setRefresh(false,true);
                }
                else {
                    instance.setRefresh(false,false);
                }
                instance.notifyListChange();
            }

            @Override
            public void onError(Throwable e) {
                instance.setRefresh(false,false);
                instance.showToast("刷新失败");
            }

            @Override
            public void onNext(WbData wbData) {
                FinalViewData fdata;
                List<FinalViewData> data=new ArrayList<>();
                for (int i = 0; i < wbData.getStatuses().size(); i++) {
                    if(i==0&&max_id!=0)
                        i=1;
                    fdata = new FinalViewData();
                    fdata.setText(wbData.getStatuses().get(i).getText())
                            .setHeadurl(wbData.getStatuses().get(i).getUser().getAvatar_large())
                            .setName(wbData.getStatuses().get(i).getUser().getName())
                            .setId(wbData.getStatuses().get(i).getId())
                            .setTime(ConvertDate.calcDate(wbData.getStatuses().get(i).getCreated_at()))
                            .setReposts_count(wbData.getStatuses().get(i).getReposts_count())
                            .setComments_count(wbData.getStatuses().get(i).getComments_count());

                    if(wbData.getStatuses().get(i).getRetweeted_statuses()!=null)
                    {
                        fdata.setRet_time(ConvertDate.calcDate(wbData.getStatuses().get(i).getRetweeted_statuses().getCreated_at()))
                                .setRet_text(wbData.getStatuses().get(i).getRetweeted_statuses().getText())
                                .setReposts_count_ret(wbData.getStatuses().get(i).getRetweeted_statuses().getReposts_count())
                                .setComments_count_ret(wbData.getStatuses().get(i).getRetweeted_statuses().getComments_count())
                                .setRet_name(wbData.getStatuses().get(i).getRetweeted_statuses().getUser().getName())
                                .setRet_headurl(wbData.getStatuses().get(i).getRetweeted_statuses().getUser().getAvatar_large())
                                .setRet_id(wbData.getStatuses().get(i).getRetweeted_statuses().getId());

                        if(wbData.getStatuses().get(i).getRetweeted_statuses().getPic_urls()!=null)
                        {
                            fdata.setRet_picurls(wbData.getStatuses().get(i).getRetweeted_statuses().getPic_urls());
                        }
                        if(wbData.getStatuses().get(i).getRetweeted_statuses().getPic_ids()!=null)
                        {
                            List<String> array=new ArrayList<>();
                            for(String ids:wbData.getStatuses().get(i).getRetweeted_statuses().getPic_ids()){
                                ids="http://ww3.sinaimg.cn/thumbnail/"+ids+".jpg";
                                array.add(ids);
                            }
                            fdata.setRet_picurls(array);
                        }
                    }
                    if(wbData.getStatuses().get(i).getPic_urls()!=null)
                    {
                        fdata.setPicurls(wbData.getStatuses().get(i).getPic_urls());
                    }
                    if(wbData.getStatuses().get(i).getPic_ids()!=null)
                    {
                        List<String> array=new ArrayList<>();
                        for(String ids:wbData.getStatuses().get(i).getPic_ids()){
                            ids="http://ww3.sinaimg.cn/thumbnail/"+ids+".jpg";
                            array.add(ids);
                        }
                        fdata.setPicurls(array);
                    }
                    if(max_id!=0)
                        StaticData.getInstance().data.add(fdata);
                    else
                        data.add(fdata);
                }
                if(max_id==0)
                    StaticData.getInstance().data=data;

            }
        };
        DataManager.getInstance().getWbData(mSubscribe, max_id);
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
    public void getToken(String code, final Context context)
    {
        Subscriber<Access_token> mSubscribe;
        mSubscribe=new Subscriber<Access_token>() {
            @Override
            public void onCompleted() {
                instance.showToast("登陆成功");
                getWbData(0);
            }

            @Override
            public void onError(Throwable e) {
                instance.showToast("登陆失败");
            }

            @Override
            public void onNext(Access_token access_token) {
                SpUtil.putString(context,"token",access_token.getAccess_token());
                SpUtil.putString(context,"uid",access_token.getUid());
            }
        };
        DataManager.getInstance().getAccess(mSubscribe,code);
    }
}
