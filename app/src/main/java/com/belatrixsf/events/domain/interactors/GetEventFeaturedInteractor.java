package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;

import javax.inject.Inject;

public class GetEventFeaturedInteractor extends AbstractInteractor<GetEventFeaturedInteractor.CallBack, Void> {


    public interface CallBack {
        void onSuccess(String urlImage);
        void onError();
    }


    @Inject
    public GetEventFeaturedInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Void... params) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess("http://www.belatrixsf.com/images/Scrum_Masters_at_Hackatrix_Lima_2014.jpg");
            }
        });


    }
}
