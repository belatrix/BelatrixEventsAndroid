package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.City;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class GetCityListInteractor extends AbstractInteractor {

    public interface CallBack {
        void onSuccess(List<City> result);
        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public GetCityListInteractor() {
    }

    public void getCityList(final GetCityListInteractor.CallBack callback) {
        disposable = eventRepository.cityList().subscribe(new Consumer<List<City>>() {
            @Override
            public void accept(List<City> cities) throws Exception {
                callback.onSuccess(cities);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError();
            }
        });
    }

}