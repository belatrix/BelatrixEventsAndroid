package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.rest.retrofit.api.UserAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.data.datasource.rest.retrofit.response.UserAuthenticationResponse;
import com.belatrix.events.domain.model.Profile;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.model.Role;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.domain.repository.UserRepository;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

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
    public Observable<ResponseBody> recoverPassword(String email) {
        return subscribeOn(mUserAPI.recoverPassword(email));
    }

    @Override
    public Observable<User> createAccount(String email) {
        return subscribeOn(mUserAPI.createAccount(email));
    }

    @Override
    public Observable<User> changePassword(String token, String oldPassword, String newPassword) {
        return subscribeOn(mUserAPI.changePassword("Token " + token, oldPassword, newPassword));
    }

    @Override
    public Observable<User> getUser(String token, int userId) {
        return subscribeOn(mUserAPI.getUserDetail("Token " + token, userId));
    }

    @Override
    public Observable<User> updateUser(String token, String fullName, String phoneNumber, int roleId) {
        return subscribeOn(mUserAPI.updateProfile("Token " + token, fullName, phoneNumber, roleId));
    }

    @Override
    public Observable<List<Role>> listRole(String token) {
        return subscribeOn(mUserAPI.listRole("Token " + token));
    }

    @Override
    public Observable<List<User>> searchUser(String token, String search) {
        return subscribeOn(mUserAPI.listUser("Token " + token, search));
    }

    @Override
    public Observable<Profile> getProfile(String token, int userId) {
        return subscribeOn(mUserAPI.getProfile("Token " + token, userId));
    }

    @Override
    public Observable<List<Project>> listIdeas(String token) {
        return subscribeOn(mUserAPI.listIdeas("Token " + token));
    }
}
