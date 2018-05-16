package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.data.datasource.rest.retrofit.request.IdeaCreateRequest;
import com.belatrix.events.data.datasource.rest.retrofit.response.CandidatesResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.IdeaCreateResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.ParticipantsResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IdeaAPI {

    @POST("/idea/create/")
    Observable<IdeaCreateResponse> createIdea(@Header("Authorization") String token, @Body IdeaCreateRequest body);

    @GET("/idea/{idea_id}/participants/")
    Observable<ParticipantsResponse> listParcipantByIdeaId(@Path("idea_id") int ideaId);


    @GET("/idea/{idea_id}/candidates/")
    Observable<CandidatesResponse> listCandidatesByIdeaId(@Header("Authorization") String token, @Path("idea_id") int ideaId);

    @POST("/idea/{idea_id}/candidate/approval/switch/")
    Observable<Integer> approveCandidate(@Header("Authorization") String token, @Path("idea_id") int ideaId, @Field("user_id") int userId);

    @POST("/idea/{idea_id}/unregister/candidate/")
    Observable<Integer> unregisterCandidate(@Header("Authorization") String token, @Path("idea_id") int ideaId, @Field("user_id") int userId);

    @POST("/idea/{idea_id}/register/candidate/")
    Observable<Integer> registerCandidate(@Header("Authorization") String token, @Path("idea_id") int ideaId, @Field("user_id") int userId);
}
