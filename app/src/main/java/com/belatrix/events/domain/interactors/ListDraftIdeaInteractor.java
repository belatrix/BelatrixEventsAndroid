package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.IdeaRepository;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ListDraftIdeaInteractor extends AbstractInteractor {

    private final IdeaRepository mIdeaRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    public ListDraftIdeaInteractor(IdeaRepository ideaRepository, AccountUtils accountUtils) {
        this.mIdeaRepository = ideaRepository;
        this.mAccountUtils = accountUtils;
    }

    public void listDraftIdea(final Callback callback, int eventId) {
        disposable = mIdeaRepository.listIdeaDraft(mAccountUtils.getToken(), eventId).subscribe(new Consumer<List<Project>>() {
            @Override
            public void accept(List<Project> projects) throws Exception {
                callback.onListIdeaLoaded(projects);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onListIdeaError();
            }
        });
    }

    public interface Callback {
        void onListIdeaLoaded(List<Project> result);

        void onListIdeaError();
    }
}
