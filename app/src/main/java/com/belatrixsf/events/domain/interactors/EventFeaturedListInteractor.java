package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;
import com.belatrixsf.events.domain.interactors.base.Interactor;
import com.belatrixsf.events.domain.model.Event;

import java.util.List;

import javax.inject.Inject;

public class EventFeaturedListInteractor extends AbstractInteractor<Callback<List<Event>>> {

    @Inject
    public EventFeaturedListInteractor(Executor threadExecutor,
                                       MainThread mainThread
    ) {
        super(threadExecutor, mainThread);
    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onResult(Event.getDummyData());
            }
        });


    }
}
