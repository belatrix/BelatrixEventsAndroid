package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.IdeaRepository;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ValidateIdeaInteractor extends AbstractInteractor {

    private final IdeaRepository mIdeaRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    public ValidateIdeaInteractor(IdeaRepository ideaRepository, AccountUtils accountUtils) {
        this.mIdeaRepository = ideaRepository;
        this.mAccountUtils = accountUtils;
    }

    public void validateIdea(final Callback callback, int ideaId) {
        disposable = mIdeaRepository.validateIdea(mAccountUtils.getToken(), ideaId).subscribe(new Consumer<Project>() {
            @Override
            public void accept(Project project) throws Exception {
                callback.onValidatedSuccessful(project);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onValidatedError();
            }
        });
    }

    public interface Callback {
        void onValidatedSuccessful(Project project);

        void onValidatedError();
    }
}
