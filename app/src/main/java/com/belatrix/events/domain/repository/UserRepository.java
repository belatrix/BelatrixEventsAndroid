package com.belatrix.events.domain.repository;


import com.belatrix.events.domain.model.User;

import io.reactivex.Observable;

public interface UserRepository {

    Observable<User> signIn(String username, String password);
}
