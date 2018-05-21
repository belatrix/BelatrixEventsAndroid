package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.rest.retrofit.response.CandidatesResponse;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Author;
import com.belatrix.events.domain.repository.IdeaRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ListCandidatesInteractor extends AbstractInteractor {

    private final IdeaRepository mIdeaRepository;

    @Inject
    ListCandidatesInteractor(IdeaRepository ideaRepository) {
        this.mIdeaRepository = ideaRepository;
    }

    public void getCandidatesListById(final Callback callback, String token, int ideaId) {
        disposable = mIdeaRepository.listCandidates(token, ideaId).subscribe(new Consumer<CandidatesResponse>() {
            @Override
            public void accept(CandidatesResponse response) throws Exception {
                List<Author> lst = new ArrayList<>();
                for (CandidatesResponse.TeamMembers teamMembers : response.getLstCandidates()) {
                    lst.add(teamMembers.getAuthor());
                }
                callback.onCandidatesSuccess(response.isCandidate(), lst);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onCandidatesError();
            }
        });
    }

    public interface Callback {
        void onCandidatesSuccess(boolean isCandidate, List<Author> result);

        void onCandidatesError();
    }
}
