package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.data.datasource.ServerCallBack;
import com.belatrixsf.events.data.datasource.rest.retrofit.server.Contributor;
import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.repository.EventRepository;

import java.util.List;

import javax.inject.Inject;

public class GetEventFeaturedInteractor extends AbstractInteractor<GetEventFeaturedInteractor.CallBack, Void> {


    public interface CallBack {
        void onSuccess(String urlImage);
        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public GetEventFeaturedInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Void... params) {
        eventRepository.getHomeEvent(new ServerCallBack<List<Contributor>>() {
            @Override
            public void onSuccess(List<Contributor> response) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess("http://www.belatrixsf.com/images/Scrum_Masters_at_Hackatrix_Lima_2014.jpg");
                    }
                });
            }

            @Override
            public void onFail(int statusCode, final String errorMessage) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError();
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError();
                    }
                });
            }
        });




    }
}
