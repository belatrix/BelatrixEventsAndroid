package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.rest.retrofit.api.UserAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.data.datasource.rest.retrofit.response.UserAuthenticationResponse;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.domain.repository.UserRepository;

import io.reactivex.Observable;

public class UserRepositoryImpl extends BaseRepository implements UserRepository {

    private final UserAPI mUserAPI;

    public UserRepositoryImpl(UserAPI userAPI) {
        this.mUserAPI = userAPI;
    }

    @Override
    public Observable<UserAuthenticationResponse> signIn(String username, String password) {
        return subscribeOn(mUserAPI.signIn(username, password));
    }

    @Override
    public Observable<String> recoverPassword(String email) {
        return subscribeOn(mUserAPI.recoverPassword(email));
    }

    @Override
    public Observable<User> createAccount(String email) {
        return subscribeOn(mUserAPI.createAccount(email));
    }

    @Override
    public Observable<User> changePassword(int userId, String oldPassword, String newPassword) {
        return subscribeOn(mUserAPI.changePassword(userId, oldPassword, newPassword));
    }

    @Override
    public Observable<User> getUser(int userId) {
        return subscribeOn(mUserAPI.getUserDetail(userId));
    }
}
