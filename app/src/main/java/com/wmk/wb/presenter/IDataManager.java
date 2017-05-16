package com.wmk.wb.presenter;

import com.wmk.wb.model.entity.retjson.Access_token;
import com.wmk.wb.model.entity.retjson.CommentsData;
import com.wmk.wb.model.entity.retjson.Statuses;
import com.wmk.wb.model.entity.retjson.User;
import com.wmk.wb.model.entity.retjson.WbData;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wmk on 2017/4/3.
 */

public interface IDataManager {
    @POST("access_token")
    Observable<Access_token> getToken(@Query("client_id") String id,@Query("client_secret") String secret,@Query("grant_type") String type,@Query("redirect_uri") String uri,@Query("code") String code);

    @GET("home_timeline.json")
    Observable<WbData> getData(@Query("access_token") String access_token,@Query("max_id") long max_id,@Query("feature") int feature);

    @GET("mentions.json")
    Observable<WbData> getMentionsData(@Query("access_token") String access_token,@Query("max_id") long max_id,@Query("feature") int feature);

    @GET("bilateral_timeline.json")
    Observable<WbData> getbilateralData(@Query("access_token") String access_token,@Query("max_id") long max_id,@Query("feature") int feature);

    @GET("show.json")
    Observable<User>getUser(@Query("access_token") String access_token,@Query("uid") String uid);

    @GET("show.json")
    Observable<CommentsData>getComments(@Query("access_token") String access_token, @Query("id") long id, @Query("max_id") long max_id);

    @POST("create.json")
    Observable<ResponseBody> sendComments(@Query("access_token") String access_token, @Query("comment") String comment ,@Query("id") long id);

    @POST("repost.json")
    Observable<ResponseBody> relay(@Query("access_token") String access_token , @Query("id") long id, @Query("status") String status);

    @POST("update.json")
    Observable<ResponseBody> create(@Query("access_token") String access_token , @Query("status") String status);
}
