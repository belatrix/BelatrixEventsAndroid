package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.ListDraftIdeaInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import java.util.List;

import javax.inject.Inject;

public class ModeratorListIdeasPresenter extends BelatrixBasePresenter<ModeratorListIdeasPresenter.View> implements ListDraftIdeaInteractor.Callback {

    private final ListDraftIdeaInteractor mListDraftIdeaInteractor;

    @Inject
    public ModeratorListIdeasPresenter(ListDraftIdeaInteractor listDraftIdeaInteractor) {
        this.mListDraftIdeaInteractor = listDraftIdeaInteractor;
    }

    @Override
    public void cancelRequests() {
        mListDraftIdeaInteractor.cancel();
    }

    public void listIdeas(int eventId) {
        mListDraftIdeaInteractor.listDraftIdea(ModeratorListIdeasPresenter.this, eventId);
    }

    @Override
    public void onListIdeaLoaded(List<Project> result) {
        view.onListLoaded(result);
    }

    @Override
    public void onListIdeaError() {
        view.onListError();
    }

    public interface View extends BelatrixBaseView {
        void onListLoaded(List<Project> result);

        void onListError();
    }
}
