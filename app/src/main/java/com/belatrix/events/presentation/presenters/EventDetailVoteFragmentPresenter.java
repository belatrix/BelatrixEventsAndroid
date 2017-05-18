package com.belatrix.events.presentation.presenters;

import com.belatrix.events.R;
import com.belatrix.events.domain.interactors.ProjectListInteractor;
import com.belatrix.events.domain.interactors.ProjectVoteInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.cache.Cache;

import java.util.List;

import javax.inject.Inject;

public class EventDetailVoteFragmentPresenter extends BelatrixBasePresenter<EventDetailVoteFragmentPresenter.View>{

    public interface View extends BelatrixBaseView {
        void showProjectList(List<Project> list);
        void onVoteSuccessful();
        void onVoteFail(String errorMessage);
        void onConfirmationDialogCreated(String message, int projectId);
        void showEmptyView();
        void onError(String errorMessage);
    }

    ProjectListInteractor interactor;
    ProjectVoteInteractor projectVoteInteractor;
    @Inject
    Cache cache;

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Inject
    public EventDetailVoteFragmentPresenter(ProjectListInteractor interactor, ProjectVoteInteractor projectVoteInteractor ) {
        this.interactor = interactor;
        this.projectVoteInteractor = projectVoteInteractor;
    }

    public void voteForProject(int projectId){
        view.showProgressIndicator();
        projectVoteInteractor.execute(new ProjectVoteInteractor.CallBack() {
            @Override
            public void onSuccess(Project result) {
                cache.saveVote(event.getId());
                view.hideProgressIndicator();
                view.onVoteSuccessful();
            }

            @Override
            public void onError() {
                view.hideProgressIndicator();
                view.onVoteFail(view.getContext().getString(R.string.dialog_title_error));
            }
        }, ProjectVoteInteractor.Params.forProject(projectId));
    }

    public void getProjectList(final int eventId) {
        view.showProgressIndicator();
        interactor.execute(new ProjectListInteractor.CallBack() {
            @Override
            public void onSuccess(final List<Project> result) {
                view.hideProgressIndicator();
                if (result.isEmpty()){
                    view.showEmptyView();
                } else {
                    view.showProjectList(result);
                }
            }

            @Override
            public void onError() {
                view.hideProgressIndicator();
                view.onError(view.getContext().getString(R.string.dialog_title_error));
            }
        }, ProjectListInteractor.Params.forEvent(eventId));
    }

    public void buildConfirmationMessage(Project project){
        boolean alreadyVoted = cache.alreadyVoted(event.getId());
        if (alreadyVoted){
            view.onVoteFail(view.getContext().getString(R.string.dialog_error_vote_already));
        } else {
            if (event.isInteractionActive()) {
                final String projectName = project.getText();
                String confirmationMessage = view.getContext().getString(R.string.event_dialog_confirm_vote, projectName);
                if (event.getInteractionConfirmationText() != null && !event.getInteractionConfirmationText().isEmpty()) {
                    confirmationMessage = String.format(event.getInteractionConfirmationText(), projectName);
                }
                confirmationMessage += "\n\n" + view.getContext().getString(R.string.dialog_option_note);
                view.onConfirmationDialogCreated(confirmationMessage, project.getId());
            } else {
                view.onVoteFail(view.getContext().getString(R.string.dialog_error_vote_disable));
            }
        }

    }

    @Override
    public void cancelRequests() {
        interactor.cancel();
        projectVoteInteractor.cancel();
    }

    public void updateFirstTime() {
        cache.updateStartAppFlag();
    }

    public boolean isFirstTime(){
        return cache.isFirstTimeStartApp();
    }

}
