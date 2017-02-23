package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.DaggerLoginComponent;
import com.belatrixsf.events.di.module.LoginModule;
import com.belatrixsf.events.presentation.presenters.LoginPresenter;
import com.belatrixsf.events.presentation.ui.activities.MainActivity;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * created by dvelasquez
 */
public class LoginFragment extends BelatrixBaseFragment implements LoginPresenter.View {

    @Inject
    LoginPresenter loginPresenter;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDependencies() {
        DaggerLoginComponent.builder().loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onLoginSuccess() {
       startActivity(MainActivity.makeIntent(getActivity()));
    }

    @Override
    public void onLoginError(String errorMessage) {
        showError(errorMessage);
    }

    @OnClick(R.id.log_in)
    public void onClickLogin(){
            loginPresenter.login("diego","diego");
    }




}
