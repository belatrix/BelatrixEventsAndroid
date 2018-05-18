package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.rest.retrofit.response.CandidatesResponse;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Author;
import com.belatrix.events.domain.repository.IdeaRepository;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ApproveCandidateInteractor extends AbstractInteractor {

    private final IdeaRepository mIdeaRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    ApproveCandidateInteractor(IdeaRepository ideaRepository, AccountUtils accountUtils) {
        this.mIdeaRepository = ideaRepository;
        this.mAccountUtils = accountUtils;
    }

    public void approveCandidate(final Callback callback, int ideaId, int userId) {
        disposable = mIdeaRepository.approveCandidate(mAccountUtils.getToken(), ideaId, userId).subscribe(new Consumer<CandidatesResponse>() {
            @Override
            public void accept(CandidatesResponse response) throws Exception {
                List<Author> lst = new ArrayList<>();
                for (CandidatesResponse.TeamMembers teamMembers : response.getLstCandidates()) {
                    lst.add(teamMembers.getAuthor());
                }
                callback.onApprovedCandidate(lst, response.isCandidate());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onApprovedCandidateError();
            }
        });
    }

    public interface Callback {
        void onApprovedCandidate(List<Author> lst, boolean isCandidate);

        void onApprovedCandidateError();
    }
}