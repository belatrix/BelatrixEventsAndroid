package com.belatrixsf.events.data.datasource.rest.retrofit.api;

import com.belatrixsf.events.data.datasource.rest.retrofit.server.Contributor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EventAPI {
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> repoContributors(
            @Path("owner") String owner,
            @Path("repo") String repo);
}
