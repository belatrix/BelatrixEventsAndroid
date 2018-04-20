package com.belatrix.events.domain.repository;


import com.belatrix.events.data.datasource.rest.retrofit.response.UserAuthenticationResponse;
import com.belatrix.events.domain.model.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface UserRepository {

    Observable<UserAuthenticationResponse> signIn(String username, String password);

    Observable<ResponseBody> recoverPassword(String email);

    Observable<User> createAccount(String email);

    Observable<User> changePassword(String token, int userId, String oldPassword, String newPassword);

    Observable<User> getUser(String token, int userId);
}
