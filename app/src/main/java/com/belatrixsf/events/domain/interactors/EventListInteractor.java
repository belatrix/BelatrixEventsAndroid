package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;
import com.belatrixsf.events.domain.model.Event;


import javax.inject.Inject;



public class EventListInteractor extends AbstractInteractor<Callback,Void> {


    @Inject
    public EventListInteractor() {

    }


    @Override
    public void run(Void ...params) {
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
