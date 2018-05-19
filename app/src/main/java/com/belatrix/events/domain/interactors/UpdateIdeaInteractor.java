package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.IdeaRepository;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class UpdateIdeaInteractor extends AbstractInteractor {

    private final IdeaRepository mIdeaRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    public UpdateIdeaInteractor(IdeaRepository ideaRepository, AccountUtils accountUtils) {
        this.mIdeaRepository = ideaRepository;
        this.mAccountUtils = accountUtils;
    }

    public void updateIdea(final Callback callback, int ideaId, String title, String description) {
        disposable = mIdeaRepository.updateIdea(mAccountUtils.getToken(), ideaId, title, description).subscribe(new Consumer<Project>() {
            @Override
            public void accept(Project project) throws Exception {
                callback.onUpdateIdeaCompleted(project);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onUpdateIdeaError();
            }
        });
    }

    public interface Callback {
        void onUpdateIdeaCompleted(Project project);

        void onUpdateIdeaError();
    }
}
