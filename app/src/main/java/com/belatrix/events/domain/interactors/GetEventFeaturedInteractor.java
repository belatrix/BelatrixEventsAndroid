package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.executor.Executor;
import com.belatrix.events.domain.executor.MainThread;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.repository.EventRepository;

import javax.inject.Inject;

public class GetEventFeaturedInteractor extends AbstractInteractor<GetEventFeaturedInteractor.CallBack, GetEventFeaturedInteractor.Params> {


    public interface CallBack {
        void onSuccess(Event event);
        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public GetEventFeaturedInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params... params) {
        Integer cityId = params[0].cityId;
        eventRepository.featured(cityId ,new ServerCallback<Event>() {
            @Override
            public void onSuccess(final Event response) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(response);
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

    @Override
    public void onError(Exception e) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                callback.onError();
            }
        });
    }

    public static class Params {

        private Integer cityId;

        private Params(Integer cityId) {
            this.cityId = cityId;
        }

        public static Params forCity(Integer cityId){
            return new Params(cityId);
        }
    }
}
