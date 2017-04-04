package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.data.datasource.ServerCallback;
import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.domain.repository.EventRepository;
import com.belatrixsf.events.utils.Constants;

import java.util.List;

import javax.inject.Inject;


public class GetEventListInteractor extends AbstractInteractor<GetEventListInteractor.CallBack,GetEventListInteractor.Params> {

    public interface CallBack {
        void onSuccess(List<Event> result);
        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public GetEventListInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params ...params) {
        String eventType = params[0].eventType;
        if (eventType.equalsIgnoreCase(Constants.EVENT_TYPE_UPCOMING)){
            eventRepository.upcomingList(serverCallback);
        } else {
            eventRepository.pastList(serverCallback);
        }
    }

    ServerCallback serverCallback = new ServerCallback<List<Event>>() {
        @Override
        public void onSuccess(final List<Event> response) {
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
        public void onError(String errorMessage) {
            runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    callback.onError();
                }
            });
        }
    };

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

        private String eventType;

        private Params(String eventType) {
            this.eventType = eventType;
        }

        public static Params forEventType(String type){
            return new Params(type);
        }
    }
}
