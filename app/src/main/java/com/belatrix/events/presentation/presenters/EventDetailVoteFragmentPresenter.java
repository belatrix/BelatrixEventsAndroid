package com.belatrix.events.presentation.presenters;

import com.belatrix.events.R;
import com.belatrix.events.domain.interactors.ListVotesInteractor;
import com.belatrix.events.domain.interactors.ProjectVoteInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Vote;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.account.AccountUtils;
import com.belatrix.events.utils.cache.Cache;

import java.util.List;

import javax.inject.Inject;

public class EventDetailVoteFragmentPresenter extends BelatrixBasePresenter<EventDetailVoteFragmentPresenter.View> implements ProjectVoteInteractor.Callback {

    private final ListVotesInteractor interactor;
    private final ProjectVoteInteractor mProjectVoteInteractor;
    private final AccountUtils mAccountUtils;

    @Inject
    Cache cache;
    private Event event;

    @Inject
    EventDetailVoteFragmentPresenter(ListVotesInteractor interactor, ProjectVoteInteractor projectVoteInteractor, AccountUtils accountUtils) {
        this.interactor = interactor;
        this.mProjectVoteInteractor = projectVoteInteractor;
        this.mAccountUtils = accountUtils;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void getProjectList(final int eventId) {
        view.showProgressIndicator();
        interactor.getInteractionList(new ListVotesInteractor.CallBack() {
            @Override
            public void onSuccess(final List<Vote> result) {
                view.hideProgressIndicator();
                if (result.isEmpty()) {
                    view.showEmptyView();
                } else {
                    view.showVoteList(result);
                }
            }

            @Override
            public void onError() {
                view.hideProgressIndicator();
                view.onError(view.getContext().getString(R.string.dialog_title_error));
            }
        }, ListVotesInteractor.Params.forEvent(eventId));
    }

    public void voteForIdea(Vote vote) {
        if (mAccountUtils.existsAccount()) {
            view.showProgressDialog();
            mProjectVoteInteractor.actionVote(EventDetailVoteFragmentPresenter.this, event.getId(), vote.getId());
        } else {
            view.onAuthorizationRequired();
        }
    }

    @Override
    public void onSuccess() {
        view.dismissProgressDialog();
        getProjectList(event.getId());
    }

    @Override
    public void onError() {
        view.dismissProgressDialog();
        view.onVoteError();
    }

    @Override
    public void cancelRequests() {
        interactor.cancel();
    }

    public void updateFirstTime() {
        cache.updateStartAppFlag();
    }

    public boolean isFirstTime() {
        return cache.isFirstTimeStartApp();
    }

    public interface View extends BelatrixBaseView {
        void showVoteList(List<Vote> list);

        void showEmptyView();

        void onError(String errorMessage);

        void onAuthorizationRequired();

        void onVoteError();
    }

}
