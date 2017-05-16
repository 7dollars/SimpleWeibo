package com.wmk.wb.presenter;

import com.wmk.wb.model.entity.KEY;
import com.wmk.wb.model.entity.retjson.Access_token;
import com.wmk.wb.model.entity.retjson.CommentsData;
import com.wmk.wb.model.entity.retjson.Statuses;
import com.wmk.wb.model.entity.retjson.User;
import com.wmk.wb.model.entity.StaticData;
import com.wmk.wb.model.entity.retjson.WbData;
import com.wmk.wb.utils.SpUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wmk on 2017/4/3.
 */

public class DataManager {
    private Retrofit retrofit;

    private static class SingletonHolder {
        private static final DataManager INSTANCE = new DataManager();
    }

    //获取单例
    public static DataManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    ////////////////////////单例模式
    public void getAccess(Subscriber<Access_token> mSubscriber, String code) {
        String baseURL = "https://api.weibo.com/oauth2/";
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        IDataManager iDataManager = retrofit.create(IDataManager.class);

        iDataManager.getToken(KEY.APP_KEY, KEY.APP_SECRET, "authorization_code", KEY.DEFAULT_PAGE, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    public void getWbData(Subscriber<WbData> mSubscriber, long max_id) {
        String baseURL = "https://api.weibo.com/2/statuses/";
        int feature = 0;
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);


        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        IDataManager iDataManager = retrofit.create(IDataManager.class);

        switch (StaticData.getInstance().getWbFlag()) {
            case 0: {
                iDataManager.getData(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), max_id, feature)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 1: {
                iDataManager.getbilateralData(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), max_id, feature)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 2: {
                feature = 1;
                iDataManager.getData(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), max_id, feature)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
            case 3: {
                iDataManager.getMentionsData(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), max_id, feature)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mSubscriber);
                break;
            }
        }

    }

    public void getLocalUser(Subscriber<User> mSubscriber) {
        String baseURL = "https://api.weibo.com/2/users/";
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        IDataManager iDataManager = retrofit.create(IDataManager.class);

        //  Log.e("123",SpUtil.getString(StaticData.getInstance().getmContext(),"uid",null));
        iDataManager.getUser(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), SpUtil.getString(StaticData.getInstance().getmContext(), "uid", null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    public void getComments(Subscriber<CommentsData> mSubscriber, long id, long max_id,String text) {
        String baseURL = "https://api.weibo.com/2/comments/";
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        IDataManager iDataManager = retrofit.create(IDataManager.class);
        iDataManager.getComments(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), id, max_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);

    }
    public void setComments(Subscriber<ResponseBody> mSubscriber, long id, String text) {
        String baseURL = "https://api.weibo.com/2/comments/";
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new RequestInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        IDataManager iDataManager = retrofit.create(IDataManager.class);

        if(text!=null)
        {
            iDataManager.sendComments(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null), text ,id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mSubscriber);
        }
        //  Log.e("123",SpUtil.getString(StaticData.getInstance().getmContext(),"uid",null));
    }
    public void relay(Subscriber<ResponseBody> mSubscriber, long id, String text) {
        String baseURL = "https://api.weibo.com/2/statuses/";
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new RequestInterceptor());
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        IDataManager iDataManager = retrofit.create(IDataManager.class);

        iDataManager.relay(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null),id,text )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
        //  Log.e("123",SpUtil.getString(StaticData.getInstance().getmContext(),"uid",null));
    }
    public void createNew(Subscriber<ResponseBody> mSubscriber, String text) {
        String baseURL = "https://api.weibo.com/2/statuses/";
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new RequestInterceptor());
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        IDataManager iDataManager = retrofit.create(IDataManager.class);

        iDataManager.create(SpUtil.getString(StaticData.getInstance().getmContext(), "token", null),text )
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
}
