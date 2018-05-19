package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.rest.retrofit.api.IdeaAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.data.datasource.rest.retrofit.response.CandidatesResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.IdeaCreateResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.ParticipantsResponse;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.IdeaRepository;

import io.reactivex.Observable;

public class IdeaRepositoryImpl extends BaseRepository implements IdeaRepository {

    private final IdeaAPI mIdeaAPI;

    public IdeaRepositoryImpl(IdeaAPI ideaAPI) {
        this.mIdeaAPI = ideaAPI;
    }

    @Override
    public Observable<IdeaCreateResponse> createIdea(String token, int authorId, int eventId, String title, String description) {
        return subscribeOn(mIdeaAPI.createIdea("Token " + token, authorId, eventId, title, description));
    }

    @Override
    public Observable<Project> updateIdea(String token, int ideaId, String title, String description) {
        return subscribeOn(mIdeaAPI.updateIdea("Token " + token, ideaId, title, description));
    }

    @Override
    public Observable<ParticipantsResponse> listParticipants(int ideaId) {
        return subscribeOn(mIdeaAPI.listParcipantByIdeaId(ideaId));
    }

    @Override
    public Observable<ParticipantsResponse> listParticipantsWithToken(String token, int ideaId) {
        return subscribeOn(mIdeaAPI.listParcipantByIdeaId("Token " + token, ideaId));
    }

    @Override
    public Observable<CandidatesResponse> listCandidates(String token, int ideaId) {
        return subscribeOn(mIdeaAPI.listCandidatesByIdeaId("Token " + token, ideaId));
    }

    @Override
    public Observable<CandidatesResponse> approveCandidate(String token, int ideaId, int userId) {
        return subscribeOn(mIdeaAPI.approveCandidate("Token " + token, ideaId, userId));
    }

    @Override
    public Observable<CandidatesResponse> unregisterCandidate(String token, int ideaId, int userId) {
        return subscribeOn(mIdeaAPI.unregisterCandidate("Token " + token, ideaId, userId));
    }

    @Override
    public Observable<CandidatesResponse> registerCandidate(String token, int ideaId, int userId) {
        return subscribeOn(mIdeaAPI.registerCandidate("Token " + token, ideaId, userId));
    }
}
