package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.SearchUserInteractor;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import java.util.List;

import javax.inject.Inject;

public class SearchUserPresenter extends BelatrixBasePresenter<SearchUserPresenter.View> implements SearchUserInteractor.Callback {

    private final SearchUserInteractor mSearchUserInterctor;

    @Inject
    public SearchUserPresenter(SearchUserInteractor searchUserInterctor) {
        this.mSearchUserInterctor = searchUserInterctor;
    }

    @Override
    public void cancelRequests() {
        mSearchUserInterctor.cancel();
    }

    public void searchUser(String user) {
        mSearchUserInterctor.searchUser(SearchUserPresenter.this, user);
    }

    @Override
    public void onRetrieveUserList(List<User> result) {
        view.listUsers(result);
    }

    @Override
    public void onRetrieveError() {
        view.onSearchError();
    }

    public interface View extends BelatrixBaseView {
        void listUsers(List<User> users);

        void onSearchError();
    }
}
