package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.executor.Executor;
import com.belatrix.events.domain.executor.MainThread;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.repository.EventRepository;
import com.belatrix.events.utils.Constants;

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
        Integer cityId = params[0].cityId;
        if (eventType.equalsIgnoreCase(Constants.EVENT_TYPE_UPCOMING)){
            eventRepository.upcomingList(cityId,serverCallback);
        } else {
            eventRepository.pastList(cityId,serverCallback);
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
        private Integer cityId;

        private Params(String eventType, Integer cityId) {
            this.eventType = eventType;
            this.cityId = cityId;
        }

        public static Params forEventType(String type, Integer cityId){
            return new Params(type,cityId);
        }
    }
}
