package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.repository.IdeaRepository;
import com.belatrix.events.utils.account.AccountUtils;

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
        disposable = mIdeaRepository.approveCandidate(mAccountUtils.getToken(), ideaId, userId).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                callback.onApprovedCandidate();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onApprovedCandidateError();
            }
        });
    }

    public interface Callback {
        void onApprovedCandidate();

        void onApprovedCandidateError();
    }
}
