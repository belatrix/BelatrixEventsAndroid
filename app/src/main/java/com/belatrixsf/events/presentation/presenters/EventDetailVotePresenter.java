package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.domain.interactors.ProjectListInteractor;
import com.belatrixsf.events.domain.interactors.ProjectVoteInteractor;
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
        void hideNoDataView();
        void showNoDataView();
    }

    ProjectListInteractor interactor;
    ProjectVoteInteractor projectVoteInteractor;
    private int eventId;

    @Inject
    public EventDetailVotePresenter( ProjectListInteractor interactor, ProjectVoteInteractor projectVoteInteractor ) {
        this.interactor = interactor;
        this.projectVoteInteractor = projectVoteInteractor;
    }

    public void voteForProject(int projectId){
        view.showProgressIndicator();

        projectVoteInteractor.execute(new ProjectVoteInteractor.CallBack() {
            @Override
            public void onSuccess(Boolean result) {
                view.hideProgressIndicator();
                    view.onVoteSuccessful();
            }

            @Override
            public void onError() {
                view.hideProgressIndicator();
                view.onVoteFail(null);
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
        interactor.execute(new ProjectListInteractor.CallBack() {
            @Override
            public void onSuccess(List<Project> result) {
                view.hideProgressIndicator();
                if (result.isEmpty()){
                    view.showNoDataView();
                } else {
                    view.showProjectList(result);
                }
            }

            @Override
            public void onError() {
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
