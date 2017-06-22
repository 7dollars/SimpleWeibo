package com.wmk.wb.model;

import com.wmk.wb.model.bean.retjson.Access_token;
import com.wmk.wb.model.bean.retjson.CommentsData;
import com.wmk.wb.model.bean.retjson.User;
import com.wmk.wb.model.bean.retjson.WbData;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wmk on 2017/4/3.
 */

public interface IDataManager {
    @POST("oauth2/access_token")
    Observable<Access_token> getToken(@Query("client_id") String id,@Query("client_secret") String secret,@Query("grant_type") String type,@Query("redirect_uri") String uri,@Query("code") String code);

    @GET("2/statuses/home_timeline.json")
    Observable<WbData> getData(@Query("access_token") String access_token,@Query("max_id") long max_id,@Query("feature") int feature);

    @GET("2/statuses/user_timeline.json")
    Observable<WbData> getUserData(@Query("access_token") String access_token,@Query("max_id") long max_id,@Query("screen_name") String screen_name);

    @GET("2/statuses/mentions.json")
    Observable<WbData> getMentionsData(@Query("access_token") String access_token,@Query("max_id") long max_id,@Query("feature") int feature);

    @GET("2/statuses/bilateral_timeline.json")
    Observable<WbData> getbilateralData(@Query("access_token") String access_token,@Query("max_id") long max_id,@Query("feature") int feature);

    @GET("2/users/show.json")
    Observable<User>getUser(@Query("access_token") String access_token,@Query("uid") String uid);

    @GET("2/comments/show.json")
    Observable<CommentsData>getComments(@Query("access_token") String access_token, @Query("id") long id, @Query("max_id") long max_id);

    @POST("2/comments/create.json")
    Observable<ResponseBody> sendComments(@Query("access_token") String access_token, @Query("comment") String comment ,@Query("id") long id);

    @POST("2/statuses/repost.json")
    Observable<ResponseBody> relay(@Query("access_token") String access_token , @Query("id") long id, @Query("status") String status);

    @POST("2/statuses/update.json")
    Observable<ResponseBody> create(@Query("access_token") String access_token , @Query("status") String status);

    @POST("2/comments/reply.json")
    Observable<ResponseBody> reply(@Query("access_token") String access_token , @Query("cid") long cid,@Query("id") long id,@Query("comment") String status);

    @GET("2/comments/to_me.json")
    Observable<WbData> toMe(@Query("access_token") String access_token,@Query("max_id") long max_id,@Query("count") int count);

    @GET("2/comments/mentions.json")
    Observable<WbData> getMentionsComments(@Query("access_token") String access_token,@Query("max_id") long max_id,@Query("count") int count);

    @GET("2/search/topics.json")
    Observable<WbData> getTopic(@Query("access_token") String access_token,@Query("q") String topic);

    @GET("2/favorites.json")
    Observable<WbData> getFavorites(@Query("access_token") String access_token,@Query("page") int page,@Query("count") int count);

    @GET("2/place/nearby_timeline.json")
    Observable<WbData> getNearby(@Query("access_token") String access_token,@Query("page") int page,@Query("count") int count,@Query("lat") String lat,@Query("long") String Long);

    @GET("portal.php")
    Observable<WbData> getHot(@Query("a") String a,@Query("page") int page,@Query("c") String c,@Query("catlog_id") String catlog_id);
}
