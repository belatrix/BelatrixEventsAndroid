package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.domain.model.City;
import com.belatrix.events.domain.model.Contributor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Project;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EventAPI {
    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> repoContributors(
            @Path("owner") String owner,
            @Path("repo") String repo);
    @GET("event/featured/")
    Observable<Event> featured(@Query("city") Integer cityId);
    @GET("event/upcoming/list/")
    Observable<List<Event>> upcomingList(@Query("city") Integer cityId);
    @GET("event/past/list/")
    Observable<List<Event>> pastList(@Query("city") Integer cityId);
    @GET("event/{event_id}/interaction/list")
    Observable<List<Project>> interactionList(@Path("event_id") int eventId);
    @PATCH("event/interaction/{interaction_id}/vote")
    Observable<Project> interactionVote(@Path("interaction_id") int interactionId);
    @GET("event/city/list/")
    Observable<List<City>> cityList();
}
