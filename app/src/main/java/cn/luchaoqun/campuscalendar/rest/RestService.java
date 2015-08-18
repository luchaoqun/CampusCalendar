package cn.luchaoqun.campuscalendar.rest;

import cn.luchaoqun.campuscalendar.model.CollectionRequest;
import cn.luchaoqun.campuscalendar.model.Feedback;
import cn.luchaoqun.campuscalendar.model.Job;
import cn.luchaoqun.campuscalendar.model.JobModel;
import cn.luchaoqun.campuscalendar.model.Like;
import cn.luchaoqun.campuscalendar.model.User;
import cn.luchaoqun.campuscalendar.model.UserReturn;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Administrator on 2015/8/9.
 */
public interface RestService {
    //--------------------------------------------注册----------------------------------------------
    @Headers("Content-Type:application/json")
    @POST("/users")
    void postRegister(@Body User user, Callback<UserReturn> callback);
    //--------------------------------------------登录----------------------------------------------
    @GET("/login")
    void getLogin(@Query("username") String username,
                  @Query("password") String password, Callback<UserReturn> callback);
    //--------------------------------------------列表----------------------------------------------
    @GET("/classes/Job")
    void getJobs(@Query("limit") int limit, @Query("skip") int skip,
                 @Query("order") String order, Callback<JobModel> callback);

    @GET("/classes/Job/{id}")
    void getJobById(@Path("id") String objectId, Callback<Job> callback);

    @GET("/classes/Job")
    void getJobLikes(@Query("where") String like, Callback<JobModel> callback);

    @GET("/classes/Job")//获取收藏列表------>暂时弃用
    void getUser(@Query("where") CollectionRequest collectionRequest, Callback<UserReturn> callback);
    //--------------------------------------------收藏----------------------------------------------
    @Headers("Content-Type:application/json")
    @PUT("/classes/Job/{objectId}")
    void putLike(@Path("objectId") String objectId, @Body Like like, Callback<Object> callback);

    @Headers("Content-Type:application/json")
    @PUT("/users/{id}")//---------------->暂时弃用
    void putCollection(@Header("X-AVOSCloud-Session-Token") String token, @Path("id") String objectId, @Body User user, Callback<Object> callback);
    //--------------------------------------------反馈----------------------------------------------
    @Headers("Content-Type:application/json")
    @POST("/classes/Feedback")
    void postFeedback(@Body Feedback feedback, Callback<Object> callback);
}
