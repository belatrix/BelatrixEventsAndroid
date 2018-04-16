package com.belatrix.events.presentation.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.presenters.LoginFragmentPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.Validator;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BelatrixBaseFragment implements LoginFragmentPresenter.View {


    @BindView(R.id.til_user)
    TextInputLayout tilUser;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.tv_error)
    TextView tvError;

    @Inject
    AccountUtils accountUtils;

    @Inject
    Validator mValidator;

    @Inject
    LoginFragmentPresenter loginFragmentPresenter;

    private LoginCallback mLoginCallback;

    public static Fragment newInstance(Context context) {
        Bundle args = new Bundle();
        return Fragment.instantiate(context, LoginFragment.class.getName(), args);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginCallback) {
            mLoginCallback = (LoginCallback) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LoginCallback) {
            mLoginCallback = (LoginCallback) activity;
        }
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        loginFragmentPresenter.setView(LoginFragment.this);
    }

    @Override
    protected void initViews() {
        tilUser.setErrorEnabled(true);
        tilPassword.setErrorEnabled(true);
        setTitle(getString(R.string.login_title));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @OnClick(R.id.bt_login)
    public void onClickContinueEvent() {
        String username = "", password = "", userError = "", passwordError = "";
        tilUser.setError(null);
        tilPassword.setError(null);
        tvError.setVisibility(View.GONE);
        if (tilUser.getEditText() != null) {
            username = tilUser.getEditText().getText().toString();
            userError = mValidator.validateStringField(getString(R.string.hint_user), username);
            if (!userError.isEmpty()) {
                tilUser.setError(userError);
            }
        }
        if (tilPassword.getEditText() != null) {
            password = tilPassword.getEditText().getText().toString();
            passwordError = mValidator.validateStringField(getString(R.string.field_empty), password);
            if (!passwordError.isEmpty()) {
                tilUser.setError(passwordError);
            }
        }
        if (passwordError.isEmpty() && userError.isEmpty()) {
            loginFragmentPresenter.signIn(username, password);
        }
    }

    @OnClick(R.id.tv_recover_password)
    public void onClickRecoverPasswordEvent() {
        replaceFragment(RecoverPasswordFragment.newInstance(getContext()), true);
    }

    @OnClick(R.id.tv_create_account)
    public void onClickCreateAccountEvent() {

    }

    @Override
    public void onLoginSuccessful() {
        mLoginCallback.onLoginSuccessful();
    }

    @Override
    public void onLoginError() {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(R.string.error_server_login);
    }

    public interface LoginCallback {
        void onLoginSuccessful();
    }
}
