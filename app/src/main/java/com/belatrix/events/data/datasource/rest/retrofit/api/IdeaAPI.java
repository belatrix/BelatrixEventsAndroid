package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.data.datasource.rest.retrofit.request.IdeaCreateRequest;
import com.belatrix.events.data.datasource.rest.retrofit.response.IdeaCreateResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IdeaAPI {

    @POST("/idea/create/")
    Observable<IdeaCreateResponse> createIdea(@Header("Authorization") String token, @Body IdeaCreateRequest body);

}
