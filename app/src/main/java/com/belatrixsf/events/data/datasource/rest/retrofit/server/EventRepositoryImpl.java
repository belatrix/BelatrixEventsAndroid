package com.belatrixsf.events.data.datasource.rest.retrofit.server;

import com.belatrixsf.events.data.datasource.ServerCallback;
import com.belatrixsf.events.data.datasource.rest.retrofit.api.EventAPI;
import com.belatrixsf.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrixsf.events.domain.model.City;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.domain.model.Project;
import com.belatrixsf.events.domain.repository.EventRepository;

import java.util.List;

import retrofit2.Call;

/**
 * Created by diegoveloper on 3/31/17.
 */

public class EventRepositoryImpl extends BaseRepository implements EventRepository {

    EventAPI eventAPI;

    public EventRepositoryImpl(EventAPI eventAPI) {
        this.eventAPI = eventAPI;
    }

    @Override
    public void getHomeEvent(ServerCallback<List<Contributor>> callBack) {
        Call<List<Contributor>> call = eventAPI.repoContributors("square", "retrofit");
        executeRequest(callBack, call);
    }

    @Override
    public void featured(Integer cityId, ServerCallback<Event> callBack) {
        Call<Event> call = eventAPI.featured(cityId);
        executeRequest(callBack, call);
    }

    @Override
    public void upcomingList(Integer cityId, ServerCallback<List<Event>> callBack) {
        Call<List<Event>> call = eventAPI.upcomingList(cityId);
        executeRequest(callBack, call);
    }

    @Override
    public void pastList(Integer cityId, ServerCallback<List<Event>> callBack) {
        Call<List<Event>> call = eventAPI.pastList(cityId);
        executeRequest(callBack, call);
    }

    @Override
    public void interactionList(int eventId, ServerCallback<List<Project>> callBack) {
        Call<List<Project>> call = eventAPI.interactionList(eventId);
        executeRequest(callBack, call);
    }


    @Override
    public void interactionVote(int interactionId, ServerCallback<Project> callBack) {
        Call<Project> call = eventAPI.interactionVote(interactionId);
        executeRequest(callBack, call);
    }

    @Override
    public void cityList(ServerCallback<List<City>> callBack) {
        Call<List<City>> call = eventAPI.cityList();
        executeRequest(callBack, call);
    }
}
