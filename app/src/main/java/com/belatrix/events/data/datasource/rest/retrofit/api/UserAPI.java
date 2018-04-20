package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.data.datasource.rest.retrofit.response.UserAuthenticationResponse;
import com.belatrix.events.domain.model.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {

    @POST("/user/authenticate/")
    @FormUrlEncoded
    Observable<UserAuthenticationResponse> signIn(@Field("username") String username, @Field("password") String password);

    @POST("/user/create/")
    @FormUrlEncoded
    Observable<User> createAccount(@Field("email") String email);

    @POST("/user/recover/")
    @FormUrlEncoded
    Observable<ResponseBody> recoverPassword(@Field("email") String email);

    @PATCH("/user/{user_id}/update/password/")
    @FormUrlEncoded
    Observable<User> changePassword(@Header("Authorization") String authorization, @Path("user_id") int user_id, @Field("current_password") String current_password, @Field("new_password") String new_password);

    @GET("/user/{user_id}/")
    Observable<User> getUserDetail(@Header("Authorization") String authorization, @Path("user_id") int user_id);
}
