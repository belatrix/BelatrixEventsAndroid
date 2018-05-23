package com.belatrix.events.domain.repository;


import com.belatrix.events.data.datasource.rest.retrofit.response.UserAuthenticationResponse;
import com.belatrix.events.domain.model.Profile;
import com.belatrix.events.domain.model.Role;
import com.belatrix.events.domain.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface UserRepository {

    Observable<UserAuthenticationResponse> signIn(String username, String password);

    Observable<ResponseBody> recoverPassword(String email);

    Observable<User> createAccount(String email);

    Observable<User> changePassword(String token, String oldPassword, String newPassword);

    Observable<User> getUser(String token, int userId);

    Observable<User> updateUser(String token, String fullName, String phoneNumber, int roleId);

    Observable<List<Role>> listRole(String token);

    Observable<List<User>> searchUser(String token, String search);

    Observable<Profile> getProfile(String token, int userId);
}
