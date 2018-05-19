package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.UpdateIdeaInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import javax.inject.Inject;

public class UpdateIdeaPresenter extends BelatrixBasePresenter<UpdateIdeaPresenter.View> implements UpdateIdeaInteractor.Callback {

    private final UpdateIdeaInteractor mUpdateIdeaInteractor;

    @Inject
    public UpdateIdeaPresenter(UpdateIdeaInteractor updateIdeaInteractor) {
        this.mUpdateIdeaInteractor = updateIdeaInteractor;
    }

    public void updateIdea(int ideaId, String title, String description) {
        view.showProgressDialog();
        mUpdateIdeaInteractor.updateIdea(UpdateIdeaPresenter.this, ideaId, title, description);
    }

    @Override
    public void cancelRequests() {
        view.dismissProgressDialog();
        mUpdateIdeaInteractor.cancel();
    }

    @Override
    public void onUpdateIdeaCompleted(Project project) {
        view.dismissProgressDialog();
        view.updateIdeaCompleted(project);
    }

    @Override
    public void onUpdateIdeaError() {
        view.dismissProgressDialog();
        view.onUpdateIdeaError();
    }

    public interface View extends BelatrixBaseView {
        void updateIdeaCompleted(Project project);

        void onUpdateIdeaError();
    }
}
