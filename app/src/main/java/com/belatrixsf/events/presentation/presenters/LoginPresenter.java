package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.domain.interactors.LoginInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBaseView;

import javax.inject.Inject;


public class LoginPresenter extends BelatrixBasePresenter<LoginPresenter.View> implements Callback<String> {

    public interface View extends BelatrixBaseView {
        void onLoginSuccess();
        void onLoginError(String errorMessage);
    }

    LoginInteractor loginInteractor;

    @Inject
    public LoginPresenter(View view,LoginInteractor loginInteractor){
        super(view);
        this.loginInteractor = loginInteractor;
    }

    public void login(String username, String password) {
        view.showProgressDialog();
        loginInteractor.execute(this, LoginInteractor.Params.forUser(username,password));
    }


    @Override
    public void onResult(String result) {
        view.dismissProgressDialog();
        view.onLoginSuccess();
    }

    @Override
    public void onError(String errorMessage) {
        view.dismissProgressDialog();
        view.onLoginError(errorMessage);
    }


    @Override
    public void cancelRequests() {

    }
}
