package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.CreateIdeaInteractor;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

public class IdeaAddPresenter extends BelatrixBasePresenter<IdeaAddPresenter.View> implements CreateIdeaInteractor.Callback {

    private final CreateIdeaInteractor mCreateIdeaInteractor;
    private final AccountUtils mAccountUtils;

    @Inject
    IdeaAddPresenter(AccountUtils accountUtils, CreateIdeaInteractor createIdeaInteractor) {
        this.mCreateIdeaInteractor = createIdeaInteractor;
        this.mAccountUtils = accountUtils;
    }

    @Override
    public void cancelRequests() {
        mCreateIdeaInteractor.cancel();
    }

    public void createIdea(int eventId, String title, String detail) {
        if (!mAccountUtils.existsAccount()) {
            view.authenticationFailed();
            return;
        }
        String token = mAccountUtils.getToken();
        int authorId = mAccountUtils.getUserId();
        mCreateIdeaInteractor.createIdea(IdeaAddPresenter.this, token, authorId, eventId, title, detail);
    }

    @Override
    public void onIdeaCreated() {
        if(view == null){
            return;
        }
        view.onIdeaCreated();
    }

    @Override
    public void onError() {
        if(view == null){
            return;
        }
        view.onIdeaError();
    }

    public interface View extends BelatrixBaseView {
        void onIdeaCreated();

        void onIdeaError();

        void authenticationFailed();
    }
}
