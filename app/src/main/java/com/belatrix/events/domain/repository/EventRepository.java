package com.belatrix.events.domain.repository;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.data.datasource.rest.retrofit.server.Contributor;
import com.belatrix.events.domain.model.City;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Project;

import java.util.List;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EventRepository {

    void getHomeEvent(ServerCallback<List<Contributor>> callBack);
    void featured(Integer cityId, ServerCallback<Event> callBack);
    void upcomingList(Integer cityId, ServerCallback<List<Event>> callBack);
    void pastList(Integer cityId, ServerCallback<List<Event>> callBack);
    void interactionList(int eventId, ServerCallback<List<Project>> callBack);
    void interactionVote(int interactionId, ServerCallback<Project> callBack);
    void cityList(ServerCallback<List<City>> callBack);
}
