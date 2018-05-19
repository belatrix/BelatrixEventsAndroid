package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.data.datasource.rest.retrofit.response.CandidatesResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.IdeaCreateResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.ParticipantsResponse;
import com.belatrix.events.domain.model.Project;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IdeaAPI {

    @POST("/idea/create/")
    @FormUrlEncoded
    Observable<IdeaCreateResponse> createIdea(@Header("Authorization") String token, @Field("author") int authorId, @Field("event") int eventId, @Field("title") String title, @Field("description") String description);

    @PATCH("/idea/{idea_id}/")
    @FormUrlEncoded
    Observable<Project> updateIdea(@Header("Authorization") String token, @Path("idea_id") int ideaId, @Field("title") String title, @Field("description") String description);

    @GET("/idea/{idea_id}/participants/")
    Observable<ParticipantsResponse> listParcipantByIdeaId(@Path("idea_id") int ideaId);

    @GET("/idea/{idea_id}/participants/")
    Observable<ParticipantsResponse> listParcipantByIdeaId(@Header("Authorization") String token, @Path("idea_id") int ideaId);

    @GET("/idea/{idea_id}/candidates/")
    Observable<CandidatesResponse> listCandidatesByIdeaId(@Header("Authorization") String token, @Path("idea_id") int ideaId);

    @POST("/idea/{idea_id}/candidate/approval/switch/")
    @FormUrlEncoded
    Observable<CandidatesResponse> approveCandidate(@Header("Authorization") String token, @Path("idea_id") int ideaId, @Field("user_id") int userId);

    @POST("/idea/{idea_id}/unregister/candidate/")
    @FormUrlEncoded
    Observable<CandidatesResponse> unregisterCandidate(@Header("Authorization") String token, @Path("idea_id") int ideaId, @Field("user_id") int userId);

    @POST("/idea/{idea_id}/register/candidate/")
    @FormUrlEncoded
    Observable<CandidatesResponse> registerCandidate(@Header("Authorization") String token, @Path("idea_id") int ideaId, @Field("user_id") int userId);
}
