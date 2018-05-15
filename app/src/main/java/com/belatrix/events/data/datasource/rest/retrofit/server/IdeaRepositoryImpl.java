package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.rest.retrofit.api.IdeaAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.data.datasource.rest.retrofit.request.IdeaCreateRequest;
import com.belatrix.events.data.datasource.rest.retrofit.response.CandidatesResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.IdeaCreateResponse;
import com.belatrix.events.data.datasource.rest.retrofit.response.ParticipantsResponse;
import com.belatrix.events.domain.repository.IdeaRepository;

import io.reactivex.Observable;

public class IdeaRepositoryImpl extends BaseRepository implements IdeaRepository {

    private final IdeaAPI mIdeaAPI;

    public IdeaRepositoryImpl(IdeaAPI ideaAPI) {
        this.mIdeaAPI = ideaAPI;
    }

    @Override
    public Observable<IdeaCreateResponse> createIdea(String token, int authorId, int eventId, String title, String description) {
        IdeaCreateRequest request = new IdeaCreateRequest();
        request.setAuthor(authorId);
        request.setEvent(eventId);
        request.setTitle(title);
        request.setDescription(description);
        return subscribeOn(mIdeaAPI.createIdea("Token " + token, request));
    }

    @Override
    public Observable<ParticipantsResponse> listParticipants(int ideaId) {
        return subscribeOn(mIdeaAPI.listParcipantByIdeaId(ideaId));
    }

    @Override
    public Observable<CandidatesResponse> listCandidates(String token, int ideaId) {
        return subscribeOn(mIdeaAPI.listCandidatesByIdeaId("Token " + token, ideaId));
    }
}
