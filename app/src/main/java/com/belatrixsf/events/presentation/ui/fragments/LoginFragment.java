package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.presentation.presenters.LoginPresenter;
import com.belatrixsf.events.presentation.ui.activities.LoginActivity;
import com.belatrixsf.events.presentation.ui.activities.MainActivity;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrixsf.events.utils.CustomDomainEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by dvelasquez
 */
public class LoginFragment extends BelatrixBaseFragment implements LoginPresenter.View {

    @Inject
    LoginPresenter presenter;
    @BindView(R.id.username)
    CustomDomainEditText usernameEditText;

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
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        presenter.setView(this);
    }

    @Override
    protected void initViews() {
        usernameEditText.setDefaultDomain("@belatrixsf.com");
        usernameEditText.setDefaultUsername(getString(R.string.hint_username));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onLoginSuccess() {
        Bundle bundle = new Bundle();
        bundle.putInt(LoginActivity.LOGIN_PARAM,LoginActivity.IS_LOGGED);
       startActivity(MainActivity.makeIntent(getActivity(),bundle));
        fragmentListener.finishActivity();
    }

    @Override
    public void onLoginError(String errorMessage) {
        showError(errorMessage);
    }

    @OnClick(R.id.log_in)
    public void onClickLogin(){
        presenter.login("diego","diego");
    }

    @OnClick(R.id.log_in_as_guest)
    public void onClickGuest(){
        Bundle bundle = new Bundle();
        bundle.putInt(LoginActivity.LOGIN_PARAM,LoginActivity.IS_GUEST);
        startActivity(MainActivity.makeIntent(getActivity(),bundle));
        fragmentListener.finishActivity();
    }


    @Override
    public void onDestroyView() {
        presenter.cancelRequests();
        super.onDestroyView();
    }
}
