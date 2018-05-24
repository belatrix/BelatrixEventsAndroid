package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.AddParticipantInteractor;
import com.belatrix.events.domain.interactors.SearchUserInteractor;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import java.util.List;

import javax.inject.Inject;

public class SearchUserPresenter extends BelatrixBasePresenter<SearchUserPresenter.View> implements SearchUserInteractor.Callback, AddParticipantInteractor.Callback {

    private final SearchUserInteractor mSearchUserInterctor;
    private final AddParticipantInteractor mAddParticipantInteractor;

    @Inject
    SearchUserPresenter(SearchUserInteractor searchUserInterctor, AddParticipantInteractor addParticipantInteractor) {
        this.mSearchUserInterctor = searchUserInterctor;
        this.mAddParticipantInteractor = addParticipantInteractor;
    }

    @Override
    public void cancelRequests() {
        mSearchUserInterctor.cancel();
        mAddParticipantInteractor.cancel();
    }

    public void searchUser(String user) {
        view.showProgressDialog();
        mSearchUserInterctor.searchUser(SearchUserPresenter.this, user);
    }

    @Override
    public void onRetrieveUserList(List<User> result) {
        view.dismissProgressDialog();
        view.listUsers(result);
    }

    @Override
    public void onRetrieveError() {
        view.dismissProgressDialog();
        view.onSearchError();
    }

    public void addParticipant(int userId, int ideaId) {
        view.showProgressDialog();
        mAddParticipantInteractor.addParticipant(SearchUserPresenter.this, ideaId, userId);
    }

    @Override
    public void onParticipantAdded() {
        view.dismissProgressDialog();
        view.onParticipantAdded();
    }

    @Override
    public void onParticipantError() {
        view.dismissProgressDialog();
        view.onErrorParticipant();
    }

    public interface View extends BelatrixBaseView {
        void listUsers(List<User> users);

        void onSearchError();

        void onParticipantAdded();

        void onErrorParticipant();
    }
}
