package com.belatrixsf.events.data.datasource.rest.retrofit.api;

import com.belatrixsf.events.data.datasource.rest.retrofit.server.Contributor;
import com.belatrixsf.events.domain.model.City;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.domain.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EventAPI {
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> repoContributors(
            @Path("owner") String owner,
            @Path("repo") String repo);
    @GET("event/featured/")
    Call<Event> featured(@Query("city") Integer cityId);
    @GET("event/upcoming/list/")
    Call<List<Event>> upcomingList(@Query("city") Integer cityId);
    @GET("event/past/list/")
    Call<List<Event>> pastList(@Query("city") Integer cityId);
    @GET("event/{event_id}/interaction/list")
    Call<List<Project>> interactionList(@Path("event_id") int eventId);
    @PATCH("event/interaction/{interaction_id}/vote")
    Call<Project> interactionVote(@Path("interaction_id") int interactionId);
    @GET("event/city/list/")
    Call<List<City>> cityList();
}
