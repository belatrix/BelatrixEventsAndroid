package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.rest.retrofit.api.EventAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.domain.model.City;
import com.belatrix.events.domain.model.Contributor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by diegoveloper on 3/31/17.
 */

public class EventRepositoryImpl extends BaseRepository implements EventRepository {

    EventAPI eventAPI;

    public EventRepositoryImpl(EventAPI eventAPI) {
        this.eventAPI = eventAPI;
    }

    @Override
    public Observable<List<Contributor>> getHomeEvent() {
        //Call<List<Contributor>> call = eventAPI.repoContributors("square", "retrofit");
        //executeRequest(callBack, call);
        return null;
    }

    @Override
    public Observable<Event> featured(Integer cityId) {
        return subscribeOn(eventAPI.featured(cityId));
    }

    @Override
    public Observable<List<Event>> upcomingList(Integer cityId) {
        return subscribeOn(eventAPI.upcomingList(cityId));
    }

    @Override
    public Observable<List<Event>> pastList(Integer cityId) {
        return subscribeOn(eventAPI.pastList(cityId));
    }

    @Override
    public Observable<List<Project>> interactionList(int eventId) {
       return subscribeOn(eventAPI.interactionList(eventId));
    }


    @Override
    public Observable<Project> interactionVote(int interactionId) {
        return eventAPI.interactionVote(interactionId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<City>> cityList() {
        return eventAPI.cityList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
