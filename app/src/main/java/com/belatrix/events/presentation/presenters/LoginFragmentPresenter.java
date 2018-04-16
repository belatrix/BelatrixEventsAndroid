package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.SignInInteractor;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import javax.inject.Inject;

public class LoginFragmentPresenter extends BelatrixBasePresenter<LoginFragmentPresenter.View> implements SignInInteractor.Callback {

    private final SignInInteractor mSignInInteractor;

    @Inject
    LoginFragmentPresenter(SignInInteractor signInInteractor) {
        this.mSignInInteractor = signInInteractor;
    }

    public void signIn(String username, String password) {
        view.showProgressDialog();
        mSignInInteractor.signIn(this, username, password);
    }

    @Override
    public void cancelRequests() {
        mSignInInteractor.cancel();
    }

    @Override
    public void onSignInSuccessful() {
        if (view == null) {
            return;
        }
        view.dismissProgressDialog();
        view.onLoginSuccessful();
    }

    @Override
    public void onSignInError() {
        view.dismissProgressDialog();
        view.onLoginError();
    }

    public interface View extends BelatrixBaseView {
        void onLoginSuccessful();

        void onLoginError();
    }
}
