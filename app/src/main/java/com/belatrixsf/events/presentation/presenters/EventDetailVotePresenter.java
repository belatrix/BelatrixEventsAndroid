package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.domain.interactors.ProjectListInteractor;
import com.belatrixsf.events.domain.interactors.ProjectVoteInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;
import com.belatrixsf.events.domain.model.Project;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBaseView;

import java.util.List;

import javax.inject.Inject;


public class EventDetailVotePresenter extends BelatrixBasePresenter<EventDetailVotePresenter.View>{

    public interface View extends BelatrixBaseView {
        void showProjectList(List<Project> list);
        void onVoteSuccessful();
        void onVoteFail(String errorMessage);
    }

    ProjectListInteractor interactor;
    ProjectVoteInteractor projectVoteInteractor;
    private int eventId;

    @Inject
    public EventDetailVotePresenter(View view, ProjectListInteractor interactor, ProjectVoteInteractor projectVoteInteractor ) {
        super(view);
        this.interactor = interactor;
        this.projectVoteInteractor = projectVoteInteractor;
    }

    public void voteForProject(int projectId){
        view.showProgressIndicator();
        projectVoteInteractor.execute(new Callback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                view.hideProgressIndicator();
                    view.onVoteSuccessful();
            }

            @Override
            public void onError(String errorMessage) {
                view.hideProgressIndicator();
                view.onVoteFail(errorMessage);
            }
        }, ProjectVoteInteractor.Params.forProject(projectId));
    }

    public void getProjectList(final int eventId) {
       getProjectList(eventId,false);
    }

    public void getProjectListOrdered(final int eventId) {
        getProjectList(eventId,true);
    }

    private void getProjectList(final int eventId, boolean orderRequired) {
        view.showProgressIndicator();
        this.eventId = eventId;
        interactor.execute(new Callback<List<Project>>() {
            @Override
            public void onResult(List<Project> result) {
                view.hideProgressIndicator();
                view.showProjectList(result);
            }

            @Override
            public void onError(String errorMessage) {
                view.hideProgressIndicator();
            }
        }, ProjectListInteractor.Params.forEvent(eventId, orderRequired));
    }


    @Override
    public void cancelRequests() {
        interactor.cancel();
        projectVoteInteractor.cancel();
    }
}
