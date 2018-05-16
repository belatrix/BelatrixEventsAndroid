package com.belatrix.events.domain.repository;

import com.belatrix.events.data.datasource.rest.retrofit.response.CandidatesResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.IdeaCreateResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.ParticipantsResponse;

import io.reactivex.Observable;

public interface IdeaRepository {

    Observable<IdeaCreateResponse> createIdea(String token, int authorId, int eventId, String title, String description);

    Observable<ParticipantsResponse> listParticipants(int ideaId);

    Observable<CandidatesResponse> listCandidates(String token, int ideaId);

    Observable<Integer> approveCandidate(String token, int ideaId, int userId);

    Observable<Integer> unregisterCandidate(String token, int ideaId, int userId);

    Observable<Integer> registerCandidate(String token, int ideaId, int userId);
}
