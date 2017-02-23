package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.domain.interactors.LoginInteractor;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBaseView;

import javax.inject.Inject;

import timber.log.Timber;


public class LoginPresenter extends BelatrixBasePresenter<LoginPresenter.View> implements LoginInteractor.Callback  {

    public interface View extends BelatrixBaseView {
        void onLoginSuccess();
        void onLoginError(String errorMessage);
    }

    @Inject
    LoginInteractor loginInteractor;

    @Inject
    public LoginPresenter(View view) {
        setView(view);
    }


    public void login(String username, String password) {
        view.showProgressDialog();
        loginInteractor.execute(this);
    }


    @Override
    public void onLoginSuccess() {
        view.dismissProgressDialog();
        Timber.d("presenter onLoginSuccess");
        view.onLoginSuccess();
    }

    @Override
    public void onLoginError(String errorMessage) {
        view.dismissProgressDialog();
        view.onLoginError(errorMessage);
    }
}
