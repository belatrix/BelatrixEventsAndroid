package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.repository.EventRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class GetEventFeaturedInteractor extends AbstractInteractor {

    public interface CallBack {
        void onSuccess(Event event);
        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public GetEventFeaturedInteractor() {
    }

    public void getFeatured(final GetEventFeaturedInteractor.CallBack callback, Params params) {
        Integer cityId = params.cityId;
        disposable = eventRepository.featured(cityId).subscribe(new Consumer<Event>() {
            @Override
            public void accept(Event event) throws Exception {
                callback.onSuccess(event);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
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
