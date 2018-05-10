package com.belatrix.events.domain.repository;

import android.arch.lifecycle.LiveData;

import com.belatrix.events.data.datasource.common.Resource;
import com.belatrix.events.domain.model.City;
import com.belatrix.events.domain.model.Contributor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.model.Vote;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EventRepository {

    Observable<List<Contributor>> getHomeEvent();

    Observable<Event> featured(Integer cityId);

    Observable<List<Event>> upcomingList(Integer cityId);

    Observable<List<Event>> pastList(Integer cityId);

    Observable<List<Project>> interactionList(int eventId);

    Observable<List<Vote>> voteList(int eventId);

    Observable<Project> interactionVote(int interactionId);

    Observable<List<City>> cityList();

    LiveData<Resource<List<Event>>> getListEvent(Integer cityId);
}
