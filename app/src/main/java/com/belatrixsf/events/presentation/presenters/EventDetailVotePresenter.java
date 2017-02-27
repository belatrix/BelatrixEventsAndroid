package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.domain.interactors.EventListInteractor;
import com.belatrixsf.events.domain.interactors.ProjectListInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.domain.model.Project;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBaseView;

import java.util.List;

import javax.inject.Inject;


public class EventDetailVotePresenter extends BelatrixBasePresenter<EventDetailVotePresenter.View> implements Callback<List<Project>> {

    public interface View extends BelatrixBaseView {
        void showProjectList(List<Project> list);
    }

    @Inject
    ProjectListInteractor interactor;

    @Inject
    public EventDetailVotePresenter(View view) {
        super(view);
    }


    public void getProjectList(final int eventId) {
        view.showProgressIndicator();
        interactor.execute(this, ProjectListInteractor.Params.forEvent(eventId));
    }


    @Override
    public void onResult(List<Project> list) {
        view.hideProgressIndicator();
        view.showProjectList(list);
    }


    @Override
    public void onError(String errorMessage) {
        view.hideProgressIndicator();
    }

    @Override
    public void cancelRequests() {

    }
}
