package com.wmk.wb.presenter;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.DataManager;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.LocationBean;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.Access_token;
import com.wmk.wb.model.bean.retjson.Statuses;
import com.wmk.wb.model.bean.retjson.User;
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

public class MainAC extends BasePresenter{
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private IMain instance;
    private Subscriber<WbData> mSubscribe;
    public MainAC(IMain instance) {
        this.instance=instance;
    }

    public void getWbData(final long max_id, final int commentflag, String name, LocationBean location)
    {
        if(name==null)
            return;

        final int flag=commentflag;
        mSubscribe = new Subscriber<WbData>() {
            @Override
            public void onCompleted() {
                    if (max_id == 0) {
                        instance.setRefresh(false, true);
                    } else {
                        instance.setRefresh(false, false);
                    }
                    instance.notifyListChange();
                }

            @Override
            public void onError(Throwable e) {
                instance.setRefresh(false, false);
                instance.showToast("刷新失败");
            }
            @Override
            public void onNext(WbData wbData) {
                FinalViewData fdata;
                int size=0;
                List<FinalViewData> data = new ArrayList<>();
                    if ((wbData.getStatuses(flag) == null)&&(wbData.getFavorites()==null)) {
                        StaticData.getInstance().data = data;
                        return;
                    }
                    if(wbData.getStatuses(flag)!=null)
                        size=wbData.getStatuses(flag).size();
                    else
                        size=wbData.getFavorites().size();

                    Statuses temp=new Statuses();
                    for (int i = 0; i < size; i++) {
                        if (i == 0 && max_id != 0)
                            i = 1;
                        if (size <= 1)
                            break;
                        fdata = new FinalViewData();
                        if(wbData.getFavorites()!=null)
                            temp=wbData.getFavorites().get(i).getStatuses();
                        else
                            temp=wbData.getStatuses(flag).get(i);

                        fdata.setText(temp.getText())
                                .setHeadurl(temp.getUser().getAvatar_large())
                                .setName(temp.getUser().getName())
                                .setId(temp.getId())
                                .setTime(ConvertDate.calcDate(temp.getCreated_at()))
                                .setReposts_count(temp.getReposts_count())
                                .setComments_count(temp.getComments_count());

                        if (temp.getRetweeted_statuses(flag) != null) {
                            fdata.setRet_time(ConvertDate.calcDate(temp.getRetweeted_statuses(flag).getCreated_at()))
                                    .setRet_text(temp.getRetweeted_statuses(flag).getText())
                                    .setReposts_count_ret(temp.getRetweeted_statuses(flag).getReposts_count())
                                    .setComments_count_ret(temp.getRetweeted_statuses(flag).getComments_count())
                                    .setRet_name(temp.getRetweeted_statuses(flag).getUser().getName())
                                    .setRet_headurl(temp.getRetweeted_statuses(flag).getUser().getAvatar_large())
                                    .setRet_id(temp.getRetweeted_statuses(flag).getId());

                            if (temp.getRetweeted_statuses(flag).getPic_urls() != null) {
                                fdata.setRet_picurls(temp.getRetweeted_statuses(flag).getPic_urls());
                            }
                            if (temp.getRetweeted_statuses(flag).getPic_ids() != null && temp.getRetweeted_statuses(flag).getPic_ids().size() != 0) {
                                List<String> array = new ArrayList<>();
                                for (String ids : temp.getRetweeted_statuses(flag).getPic_ids()) {
                                    ids = "http://ww3.sinaimg.cn/thumbnail/" + ids + ".jpg";
                                    array.add(ids);
                                }
                                fdata.setRet_picurls(array);
                            }
                        }
                        if (temp.getPic_urls() != null) {
                            fdata.setPicurls(temp.getPic_urls());
                        }
                        if (temp.getPic_ids() != null && temp.getPic_ids().size() != 0) {
                            List<String> array = new ArrayList<>();
                            for (String ids : temp.getPic_ids()) {
                                ids = "http://ww3.sinaimg.cn/thumbnail/" + ids + ".jpg";
                                array.add(ids);
                            }
                            fdata.setPicurls(array);
                        }
                        if (max_id != 0)
                            StaticData.getInstance().data.add(fdata);
                        else
                            data.add(fdata);
                    }
                    if (max_id == 0)
                        StaticData.getInstance().data = data;

                }
        };
        StaticData.getInstance().setPersonalflag(false);

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
                getWbData(0,1,null,null);
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
    public Subscriber<User> getUserSubscriber()
    {
        final Subscriber<User> mSubscriber=new Subscriber<User>() {
            @Override
            public void onCompleted() {
                unsubscribe();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {
                instance.toActivity(user);
            }
        };
        return mSubscriber;
    }
    public void setPersonalFlag(boolean flag)
    {
        StaticData.getInstance().setPersonalflag(flag);
        StaticData.getInstance().setTopicflag(false);
    }
    public void getSlideMenu()
    {
        DataManager.getInstance().getLocalUser(getUserSubscriber());
    }
    public void clearPersonalData()
    {
        StaticData.getInstance().setPersonaldata(new ArrayList<FinalViewData>());
    }
    public void setTopicFlag(boolean flag)
    {
        StaticData.getInstance().setPersonalflag(flag);
        StaticData.getInstance().setPersonalflag(false);
    }
    public  void setStaticColor(int color)
    {
        StaticData.getInstance().setThemecolor(color);
    }


}
