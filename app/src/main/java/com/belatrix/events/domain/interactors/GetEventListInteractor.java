package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.repository.EventRepository;
import com.belatrix.events.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


public class GetEventListInteractor extends AbstractInteractor {

    public interface CallBack {
        void onSuccess(List<Event> result);
        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public GetEventListInteractor() {
    }

    public void getEventList(final GetEventListInteractor.CallBack callback, Params params) {
        String eventType = params.eventType;
        Integer cityId = params.cityId;
        if (eventType.equalsIgnoreCase(Constants.EVENT_TYPE_UPCOMING)){
            getList(eventRepository.upcomingList(cityId), callback);
        } else {
            getList(eventRepository.pastList(cityId), callback);
        }
    }

    private void getList(Observable<List<Event>> observable, final GetEventListInteractor.CallBack callback){
        disposable = observable.subscribe(new Consumer<List<Event>>() {
            @Override
            public void accept(List<Event> events) throws Exception {
                callback.onSuccess(events);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
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
