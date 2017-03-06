package com.belatrixsf.events.data.datasource;

import com.belatrixsf.events.domain.repository.Repository;

import javax.inject.Inject;

/**
 * Created by dvelasquez on 2/28/17.
 */

public class InMemoryRepository implements Repository {

    @Inject
    public InMemoryRepository(){

    }

    @Override
    public boolean login(String username, String password) {

        return true;
    }
}
