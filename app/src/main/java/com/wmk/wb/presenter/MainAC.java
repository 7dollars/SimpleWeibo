package com.wmk.wb.presenter;

import android.content.Context;
import android.widget.TextView;

import com.wmk.wb.model.WbDataStack;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.DataManager;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.Access_token;
import com.wmk.wb.model.bean.retjson.Statuses;
import com.wmk.wb.model.bean.retjson.User;
import com.wmk.wb.model.bean.retjson.WbData;
import com.wmk.wb.utils.ConvertDate;
import com.wmk.wb.utils.SpUtil;
import com.wmk.wb.utils.TextUtils;
import com.wmk.wb.view.Interface.IMain;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by wmk on 2017/4/4.
 */

public class MainAC extends BasePresenter{
    private IMain instance;
    private Subscriber<FinalViewData> mSubscribe;
    private boolean isSlideMenuLoaded=false;
    public MainAC(IMain instance) {
        this.instance=instance;
    }

    public void getWbData(final Context context, final long max_id, final int commentflag)
    {
        final int flag=commentflag;
        mSubscribe = new Subscriber<FinalViewData>() {
            List<FinalViewData> data = new ArrayList<>();
            @Override
            public void onCompleted() {
                    if (max_id == 0) {
                        WbDataStack.getInstance().getTop().setData(data);
                        instance.setRefresh(false, true);
                    } else {
                        WbDataStack.getInstance().getTop().getData().addAll(data);
                        instance.setRefresh(false, false);
                    }
                    instance.setLoadMore(false);
                    instance.notifyListChange();
                }

            @Override
            public void onError(Throwable e) {
                instance.setRefresh(false, false);
                instance.showToast("刷新失败");
                instance.setLoadingFaild();
                instance.setLoadMore(false);
            }
            @Override
            public void onNext(FinalViewData wbData) {
                if(wbData!=null)
                data.add(wbData);
            }

        };
        if(max_id==0)
            WbDataStack.getInstance().getTop().setPageCount(1);
        else
            WbDataStack.getInstance().getTop().incPageCount();

        DataManager.getInstance().getWbData(context,mSubscribe, max_id,WbDataStack.getInstance().getTop().getPageCount());

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
                getWbData(context,0,1);
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
                isSlideMenuLoaded=true;
            }

            @Override
            public void onError(Throwable e) {
                isSlideMenuLoaded=false;
            }

            @Override
            public void onNext(User user) {
                instance.toActivity(user);
            }
        };
        return mSubscriber;
    }

    public void getSlideMenu()
    {
        DataManager.getInstance().getLocalUser(getUserSubscriber());
    }

    public  void setStaticColor(int color)
    {
        StaticData.getInstance().setThemecolor(color);
    }


    public boolean isSlideMenuLoaded() {
        return isSlideMenuLoaded;
    }
}
