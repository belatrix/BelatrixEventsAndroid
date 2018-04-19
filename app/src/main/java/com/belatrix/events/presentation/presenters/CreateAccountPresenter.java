package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.CreateAccountInteractor;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import javax.inject.Inject;

public class CreateAccountPresenter extends BelatrixBasePresenter<CreateAccountPresenter.View> implements CreateAccountInteractor.Callback {

    private final CreateAccountInteractor createAccountInteractor;

    @Inject
    public CreateAccountPresenter(CreateAccountInteractor createAccountInteractor) {
        this.createAccountInteractor = createAccountInteractor;
    }

    @Override
    public void cancelRequests() {
        createAccountInteractor.cancel();
    }

    public void createAccount(String email) {
        view.showProgressDialog();
        createAccountInteractor.createAccount(this, email);
    }

    @Override
    public void onCreateAccountSuccessful() {
        if (view == null) {
            return;
        }
        view.dismissProgressDialog();
        view.onCreateAccountSuccessful();
    }

    @Override
    public void onCreateAccountFailed() {
        if (view == null) {
            return;
        }
        view.dismissProgressDialog();
        view.onCreateAccountFailed();
    }

    public interface View extends BelatrixBaseView {
        void onCreateAccountSuccessful();

        void onCreateAccountFailed();
    }
}
