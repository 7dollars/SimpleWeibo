package com.wmk.wb.presenter;

import com.wmk.wb.model.DataManager;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.WbData;
import com.wmk.wb.utils.ConvertDate;
import com.wmk.wb.view.Interface.IPersonal;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by wmk on 2017/6/13.
 */

public class PersonalAC  extends BasePresenter{
    private IPersonal instance;
    private List<FinalViewData> data = new ArrayList<>();
    public PersonalAC(IPersonal instance) {
        this.instance=instance;
    }
    public void getWbData(final long max_id,String name)
    {
        Subscriber<WbData> mSubscribe;
        if(name==null)
            return;
        final int flag=1;
        mSubscribe = new Subscriber<WbData>() {

                @Override
                public void onCompleted() {
                    instance.notifyListChange();
                    instance.setLoadMore(false);
                }

                @Override
                public void onError(Throwable e) {
                    instance.showToast("刷新失败");
                    instance.setLoadMore(false);
                    instance.setLoadingFaild();
                }

                @Override
                public void onNext(WbData wbData) {
                    FinalViewData fdata;

                    if (wbData.getStatuses(flag) == null) {
                        StaticData.getInstance().Personaldata = data;
                        return;
                    }
                    for (int i = 0; i < wbData.getStatuses(flag).size(); i++) {
                        if (i == 0 && max_id != 0)
                            i = 1;
                        if (wbData.getStatuses(flag).size() <= 1)
                            break;
                        fdata = new FinalViewData();
                        fdata.setText(wbData.getStatuses(flag).get(i).getText())
                                .setHeadurl(wbData.getStatuses(flag).get(i).getUser().getAvatar_large())
                                .setName(wbData.getStatuses(flag).get(i).getUser().getName())
                                .setId(wbData.getStatuses(flag).get(i).getId())
                                .setTime(ConvertDate.calcDate(wbData.getStatuses(flag).get(i).getCreated_at()))
                                .setReposts_count(wbData.getStatuses(flag).get(i).getReposts_count())
                                .setComments_count(wbData.getStatuses(flag).get(i).getComments_count())
                                .setDescription(wbData.getStatuses(flag).get(i).getUser().getDescription())
                                .setFollowers_count(wbData.getStatuses(flag).get(i).getUser().getFollowers_count())
                                .setFriends_count(wbData.getStatuses(flag).get(i).getUser().getFriends_count())
                                .setStatuses_count(wbData.getStatuses(flag).get(i).getUser().getStatuses_count())
                                .setGender(wbData.getStatuses(flag).get(i).getUser().getGender());

                        if (wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag) != null) {
                            fdata.setRet_time(ConvertDate.calcDate(wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getCreated_at()))
                                    .setRet_text(wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getText())
                                    .setReposts_count_ret(wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getReposts_count())
                                    .setComments_count_ret(wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getComments_count())
                                    .setRet_name(wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getUser().getName())
                                    .setRet_headurl(wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getUser().getAvatar_large())
                                    .setRet_id(wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getId());

                            if (wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getPic_urls() != null) {
                                fdata.setRet_picurls(wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getPic_urls());
                            }
                            if (wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getPic_ids() != null && wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getPic_ids().size() != 0) {
                                List<String> array = new ArrayList<>();
                                for (String ids : wbData.getStatuses(flag).get(i).getRetweeted_statuses(flag).getPic_ids()) {
                                    ids = "http://ww3.sinaimg.cn/thumbnail/" + ids + ".jpg";
                                    array.add(ids);
                                }
                                fdata.setRet_picurls(array);
                            }
                        }
                        if (wbData.getStatuses(flag).get(i).getPic_urls() != null) {
                            fdata.setPicurls(wbData.getStatuses(flag).get(i).getPic_urls());
                        }
                        if (wbData.getStatuses(flag).get(i).getPic_ids() != null && wbData.getStatuses(flag).get(i).getPic_ids().size() != 0) {
                            List<String> array = new ArrayList<>();
                            for (String ids : wbData.getStatuses(flag).get(i).getPic_ids()) {
                                ids = "http://ww3.sinaimg.cn/thumbnail/" + ids + ".jpg";
                                array.add(ids);
                            }
                            fdata.setPicurls(array);
                        }
                        if (max_id != 0)
                            StaticData.getInstance().getPersonaldata().add(fdata);
                        else
                            data.add(fdata);
                    }
                    if (max_id == 0)
                        StaticData.getInstance().setPersonaldata(data);

                }
            };
        if(StaticData.getInstance().isPersonalflag())
            DataManager.getInstance().getUserWbData(mSubscribe, max_id,name);
        else
            DataManager.getInstance().getTopicData(mSubscribe,name);

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
    public void setPersonalFlag(boolean flag)
    {
        StaticData.getInstance().setPersonalflag(flag);
        StaticData.getInstance().setTopicflag(false);
    }
    public  void setData()
    {
        StaticData.getInstance().setPersonaldata(this.data);
    }
}
