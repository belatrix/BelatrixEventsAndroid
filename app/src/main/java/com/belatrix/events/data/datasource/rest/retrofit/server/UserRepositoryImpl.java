package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.rest.retrofit.api.UserAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.domain.repository.UserRepository;

import io.reactivex.Observable;

public class UserRepositoryImpl extends BaseRepository implements UserRepository {

    private final UserAPI mUserAPI;

    public UserRepositoryImpl(UserAPI userAPI) {
        this.mUserAPI = userAPI;
    }

    @Override
    public Observable<User> signIn(String username, String password) {
        return subscribeOn(mUserAPI.signIn(username, password));
    }

    @Override
    public Observable<Boolean> recoverPassword(String email) {
        return subscribeOn(mUserAPI.recoverPassword(email));
    }

    @Override
    public Observable<Boolean> createAccount(String user, String email, String name, String password) {
        return subscribeOn(mUserAPI.createAccount(user, email, name, password));
    }

    @Override
    public Observable<Boolean> changePassword(String oldPassword, String newPassword) {
        return subscribeOn(mUserAPI.changePassword(oldPassword, newPassword));
    }
}
