package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.rest.retrofit.response.CandidatesResponse;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.repository.IdeaRepository;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class RegisterCandidateInteractor extends AbstractInteractor {

    private final IdeaRepository mIdeaRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    RegisterCandidateInteractor(IdeaRepository ideaRepository, AccountUtils accountUtils) {
        this.mIdeaRepository = ideaRepository;
        this.mAccountUtils = accountUtils;
    }

    public void registerCandidate(final Callback callback, int ideaId) {
        disposable = mIdeaRepository.registerCandidate(mAccountUtils.getToken(), ideaId, mAccountUtils.getUserId()).subscribe(new Consumer<CandidatesResponse>() {
            @Override
            public void accept(CandidatesResponse response) throws Exception {
                callback.onRegisterCandidate();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onRegisterCandidateError();
            }
        });
    }

    public interface Callback {
        void onRegisterCandidate();

        void onRegisterCandidateError();
    }
}
