package com.wmk.wb.model;

import android.content.Context;
import android.util.Log;

import com.wmk.wb.model.bean.FinalCommentsData;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.KEY;
import com.wmk.wb.model.bean.LocationBean;
import com.wmk.wb.model.bean.retjson.Access_token;
import com.wmk.wb.model.bean.retjson.Comments;
import com.wmk.wb.model.bean.retjson.CommentsData;
import com.wmk.wb.model.bean.retjson.Status;
import com.wmk.wb.model.bean.retjson.Statuses;
import com.wmk.wb.model.bean.retjson.User;
import com.wmk.wb.model.bean.retjson.WbData;
import com.wmk.wb.model.bean.retjson.topicjson.TopicData;
import com.wmk.wb.model.bean.retjson.topicjson.TopicStatuses;
import com.wmk.wb.presenter.TopicAC;
import com.wmk.wb.utils.ConvertDate;
import com.wmk.wb.utils.SpUtil;
import com.wmk.wb.utils.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wmk on 2017/4/3.
 */

public class DataManager {
    private Retrofit retrofit, retrofit2;
    private IDataManager iDataManager;
    private IDataManager weicoManager;
    private int ScPageCount = 0;
    private int TopicPageCount = 0;

    public DataManager() {
        String baseURL = "https://api.weibo.com/";
        String weicoURL = "http://weicoapi.weico.cc/";
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new RequestInterceptor());
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofit2 = new Retrofit.Builder()
                .baseUrl(weicoURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        iDataManager = retrofit.create(IDataManager.class);
        weicoManager = retrofit2.create(IDataManager.class);
    }

    private static class SingletonHolder {
        private static final DataManager INSTANCE = new DataManager();
    }

    //获取单例
    public static DataManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    ////////////////////////单例模式
    public void getAccess(Subscriber<Access_token> mSubscriber, String code) {
        iDataManager.getToken(KEY.APP_KEY, KEY.APP_SECRET, "authorization_code", KEY.DEFAULT_PAGE, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    public void getUserWbData(final Context context,Subscriber<FinalViewData> mSubscriber, long max_id, String screen_name, int page) {
        iDataManager.getUserData(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), screen_name, page)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<WbData, Observable<Statuses>>() {
                    @Override
                    public Observable<Statuses> call(WbData wbData) {
                        return Observable.from(wbData.statuses);
                    }
                })
                .map(new Func1<Statuses, FinalViewData>() {
                    @Override
                    public FinalViewData call(Statuses statuses) {
                        return DataConvey(context,statuses,1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

        public void getTopicData(final Context context, Subscriber<FinalViewData> mSubscriber, String screen_name, int page) {
        iDataManager.getTopic(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), screen_name, page)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<TopicData, Observable<TopicStatuses>>() {
                    @Override
                    public Observable<TopicStatuses> call(TopicData topicData) {
                        return Observable.from(topicData.getTopicStatuses());
                    }
                })
                .map(new Func1<TopicStatuses, FinalViewData>() {
                    @Override
                    public FinalViewData call(TopicStatuses topicStatuses) {
                        return TopicDataConvey(context,topicStatuses,1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    public void getWbData(final Context context, Subscriber<FinalViewData> mSubscriber, long max_id, int page) {
        int feature = 0;

        //    Log.e("123",SpUtil.getString(StaticData.getInstance().getmContext(), "token", null));
        switch (WbDataStack.getInstance().getTop().getDataType()) {
            case 0: {
                iDataManager.getData(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), feature, page)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Func1<WbData, Observable<Statuses>>() {
                            @Override
                            public Observable<Statuses> call(WbData wbData) {
                                return Observable.from(wbData.statuses);
                            }
                        })
                        .map(new Func1<Statuses, FinalViewData>() {
                            @Override
                            public FinalViewData call(Statuses statuses) {
                                return DataConvey(context,statuses,1);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 1: {
                iDataManager.getbilateralData(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), feature, page)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Func1<WbData, Observable<Statuses>>() {
                            @Override
                            public Observable<Statuses> call(WbData wbData) {
                                return Observable.from(wbData.statuses);
                            }
                        })
                        .map(new Func1<Statuses, FinalViewData>() {
                            @Override
                            public FinalViewData call(Statuses statuses) {
                                return DataConvey(context,statuses,1);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 2: {//原创
                feature = 1;
                iDataManager.getData(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), feature, page)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Func1<WbData, Observable<Statuses>>() {
                            @Override
                            public Observable<Statuses> call(WbData wbData) {
                                return Observable.from(wbData.statuses);
                            }
                        })
                        .map(new Func1<Statuses, FinalViewData>() {
                            @Override
                            public FinalViewData call(Statuses statuses) {
                                return DataConvey(context,statuses,1);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 3: {
                iDataManager.getMentionsData(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), feature, page)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Func1<WbData, Observable<Statuses>>() {
                            @Override
                            public Observable<Statuses> call(WbData wbData) {
                                return Observable.from(wbData.statuses);
                            }
                        })
                        .map(new Func1<Statuses, FinalViewData>() {
                            @Override
                            public FinalViewData call(Statuses statuses) {
                                return DataConvey(context,statuses,1);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 4: {
                iDataManager.toMe(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), 20, page)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Func1<WbData, Observable<Statuses>>() {
                            @Override
                            public Observable<Statuses> call(WbData wbData) {
                                return Observable.from(wbData.comments);
                            }
                        })
                        .map(new Func1<Statuses, FinalViewData>() {
                            @Override
                            public FinalViewData call(Statuses statuses) {
                                return DataConvey(context,statuses,2);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 5: {
                iDataManager.getMentionsComments(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), 20, page)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Func1<WbData, Observable<Statuses>>() {
                            @Override
                            public Observable<Statuses> call(WbData wbData) {
                                return Observable.from(wbData.comments);
                            }
                        })
                        .map(new Func1<Statuses, FinalViewData>() {
                            @Override
                            public FinalViewData call(Statuses statuses) {
                                return DataConvey(context,statuses,2);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 6: {
                iDataManager.getFavorites(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), page, 20)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Func1<WbData, Observable<Status>>() {
                            @Override
                            public Observable<Status> call(WbData wbData) {
                                return Observable.from(wbData.getFavorites());
                            }
                        })
                        .map(new Func1<Status, FinalViewData>() {
                            @Override
                            public FinalViewData call(Status status) {
                                return FavoriteDataConvey(context,status,1);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 7: {
                iDataManager.getNearby(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), page, 20, LocationBean.getInstance().getLat(), LocationBean.getInstance().getLong())
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Func1<WbData, Observable<Statuses>>() {
                            @Override
                            public Observable<Statuses> call(WbData wbData) {
                                return Observable.from(wbData.statuses);
                            }
                        })
                        .map(new Func1<Statuses, FinalViewData>() {
                            @Override
                            public FinalViewData call(Statuses statuses) {
                                return DataConvey(context,statuses,1);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 8: {
                weicoManager.getHot("get_cat_list_full", page, "default", "102803")
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Func1<WbData, Observable<Statuses>>() {
                            @Override
                            public Observable<Statuses> call(WbData wbData) {
                                return Observable.from(wbData.statuses);
                            }
                        })
                        .map(new Func1<Statuses, FinalViewData>() {
                            @Override
                            public FinalViewData call(Statuses statuses) {
                                return DataConvey(context,statuses,1);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }

        }

    }

    public void getLocalUser(Subscriber<User> mSubscriber) {
        //  Log.e("123",SpUtil.getString(StaticData.getInstance().getmContext(),"uid",null));
        iDataManager.getUser(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), SpUtil.getString(StaticData.getInstance().getmContext(), "uid", null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    public void getComments(final Context context, Subscriber<FinalCommentsData> mSubscriber, final long id, long max_id) {

        iDataManager.getComments(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), id, max_id)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<CommentsData, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(CommentsData commentsData) {
                        return Observable.from(commentsData.comments);
                    }
                })
                .map(new Func1<Comments, FinalCommentsData>() {
                    @Override
                    public FinalCommentsData call(Comments comments) {
                        return CommentDataConvey(context,comments,id);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);

    }

    public void setComments(Subscriber<ResponseBody> mSubscriber, long id, long cid, String text) {

        if (text != null) {
            if (cid == 0) {
                iDataManager.sendComments(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), text, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
            } else {
                iDataManager.reply(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), cid, id, text)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
            }
        }
        //  Log.e("123",SpUtil.getString(StaticData.getInstance().getmContext(),"uid",null));
    }

    public void relay(Subscriber<ResponseBody> mSubscriber, long id, String text) {
        iDataManager.relay(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), id, text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
        //  Log.e("123",SpUtil.getString(StaticData.getInstance().getmContext(),"uid",null));
    }

    public void createNew(Subscriber<ResponseBody> mSubscriber, String text) {
        iDataManager.create(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
        //  Log.e("123",SpUtil.getString(StaticData.getInstance().getmContext(),"uid",null));
    }

    private class RequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                    .addHeader("Accept-Encoding", "*")
                    .build();
            return chain.proceed(request);
        }
    }

    private FinalViewData FavoriteDataConvey(Context context,Status temp, int flag)
    {
        return DataConvey(context,temp.getStatuses(),flag);
    }
    private FinalViewData DataConvey(Context context,Statuses temp, int flag) {
        FinalViewData fdata = new FinalViewData();
        if(temp==null)
            return fdata;
        fdata.setText(TextUtils.getWeiBoText(context, temp.getText()))
                .setHeadurl(temp.getUser().getAvatar_large())
                .setName(temp.getUser().getName())
                .setId(temp.getId())
                .setTime(ConvertDate.calcDate(temp.getCreated_at()))
                .setReposts_count(temp.getReposts_count())
                .setComments_count(temp.getComments_count())
                .setDescription(temp.getUser().getDescription())
                .setFollowers_count(temp.getUser().getFollowers_count())
                .setFriends_count(temp.getUser().getFriends_count())
                .setStatuses_count(temp.getUser().getStatuses_count())
                .setGender(temp.getUser().getGender())
                .setFollowing(temp.getUser().isfollowing());

        if (temp.getRetweeted_statuses(flag) != null) {
            fdata.setRet_time(ConvertDate.calcDate(temp.getRetweeted_statuses(flag).getCreated_at()))
                    .setRet_text(TextUtils.getWeiBoText(context, temp.getRetweeted_statuses(flag).getText()))
                    .setRet_text_with_name(TextUtils.getWeiBoText(context, "@" + temp.getRetweeted_statuses(flag).getUser().getName() + ":" + temp.getRetweeted_statuses(flag).getText()))
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
        return fdata;
    }
    private FinalViewData TopicDataConvey(Context context,TopicStatuses temp, int flag)
    {
        FinalViewData fdata = new FinalViewData();
        fdata.setText(TextUtils.getWeiBoText(context,temp.getText()))
                .setHeadurl(temp.getUser().getAvatar_large())
                .setName(temp.getUser().getName())
                .setId(temp.getId())
                .setTime(ConvertDate.calcDate(temp.getCreated_at()))
                .setReposts_count(temp.getReposts_count())
                .setComments_count(temp.getComments_count());

        if (temp.getRetweeted_statuses(flag) != null) {
            fdata.setRet_time(ConvertDate.calcDate(temp.getRetweeted_statuses(flag).getCreated_at()))
                    .setRet_text(TextUtils.getWeiBoText(context,temp.getRetweeted_statuses(flag).getText()))
                    .setRet_text_with_name(TextUtils.getWeiBoText(context,"@"+temp.getRetweeted_statuses(flag).getUser().getName()+":"+temp.getRetweeted_statuses(flag).getText()))
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
        return fdata;
    }
    private FinalCommentsData CommentDataConvey(Context context,Comments temp,long id)
    {
        FinalCommentsData fdata=new FinalCommentsData();
        fdata.setHeadurl(temp.user.getAvatar_large())
                .setName(temp.user.getName())
                .setText(temp.text)
                .setTime(ConvertDate.calcDate(temp.created_at))
                .setId(temp.id)
                .setSource(temp.source)
                .setWbId(id);
        return fdata;

    }

}
