package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.domain.model.User;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface UserAPI {

//TODO SET SIGN IN URL
    @POST("")
    Observable<User> signIn(String username, String password);
}
