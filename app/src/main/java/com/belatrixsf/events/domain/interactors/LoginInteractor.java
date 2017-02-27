package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;
import com.belatrixsf.events.domain.repository.Repository;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class LoginInteractor extends AbstractInteractor<Callback<String>,LoginInteractor.Params>  {

    private Repository mRepository;


    @Inject
    public LoginInteractor() {
    }

    @Override
    public void run(Params ...params) {
        Timber.d("running");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onResult("diego");
            }
        });
    }

    public static final class Params {
        String username;
        String password;

        public Params(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public static Params forUser(String username, String password){
            return new Params(username,password);
        }

    }
}
