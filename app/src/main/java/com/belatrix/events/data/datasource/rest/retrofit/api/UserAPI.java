package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.data.datasource.rest.retrofit.response.UserAuthenticationResponse;
import com.belatrix.events.domain.model.Profile;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.model.Role;
import com.belatrix.events.domain.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @PATCH("/user/update/password/")
    @FormUrlEncoded
    Observable<User> changePassword(@Header("Authorization") String authorization, @Field("current_password") String current_password, @Field("new_password") String new_password);

    @GET("/user/{user_id}/")
    Observable<User> getUserDetail(@Header("Authorization") String authorization, @Path("user_id") int user_id);

    @PATCH("/user/update/")
    @FormUrlEncoded
    Observable<User> updateProfile(@Header("Authorization") String token, @Field("full_name") String fullName, @Field("phone_number") String phoneNumber, @Field("role_id") int roleId);

    @GET("/user/role/list/")
    Observable<List<Role>> listRole(@Header("Authorization") String token);

    @GET("/user/list/")
    Observable<List<User>> listUser(@Header("Authorization") String token, @Query("search") String search);

    @GET("/user/profile/")
    Observable<Profile> getProfile(@Header("Authorization") String token, @Query("id") int userId);

    @GET("/user/ideas/")
    Observable<List<Project>> listIdeas(@Header("Authorization") String token);
}
