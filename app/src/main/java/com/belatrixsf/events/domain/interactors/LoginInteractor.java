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
public class LoginInteractor extends AbstractInteractor<Callback<String>>  {

    private Repository mRepository;


    @Inject
    public LoginInteractor(Executor threadExecutor,
                           MainThread mainThread
    ) {
        super(threadExecutor, mainThread);
    }


    @Override
    public void run() {
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
}
