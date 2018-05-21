package com.belatrix.events.presentation.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.presenters.LoginFragmentPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.Validator;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BelatrixBaseFragment implements LoginFragmentPresenter.View {


    @BindView(R.id.et_user)
    EditText etUser;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindString(R.string.hint_user)
    String hintUser;

    @BindString(R.string.hint_password)
    String hintPassword;

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
        if (etUser != null && etPassword != null && getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etUser.getWindowToken(), 0);
            }
            etUser.setError(null);
            etPassword.setError(null);
        }
        tvError.setVisibility(View.GONE);
        username = etUser.getText().toString();
        userError = mValidator.validateStringField(hintUser, username);
        if (!userError.isEmpty()) {
            etUser.setError(userError);
        }
        password = etPassword.getText().toString();
        passwordError = mValidator.validateStringField(hintPassword, password);
        if (!passwordError.isEmpty()) {
            etPassword.setError(passwordError);
        }
        if (passwordError.isEmpty() && userError.isEmpty()) {
            loginFragmentPresenter.signIn(username, password);
        }
    }

    @OnClick(R.id.tv_recover_password)
    public void onClickRecoverPasswordEvent() {
        if (etUser != null && getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etUser.getWindowToken(), 0);
            }
        }
        replaceFragment(RecoverPasswordFragment.newInstance(getContext()), true);
    }

    @OnClick(R.id.bt_create_account)
    public void onClickCreateAccountEvent() {
        if (etUser != null && getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etUser.getWindowToken(), 0);
            }
        }
        replaceFragment(CreateAccountFragment.newInstance(getContext()), true);
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

    @Override
    public void onChangePassword(String token, int userId) {
        replaceFragment(ChangePasswordFragment.create(getContext(), token), true);
    }

    public interface LoginCallback {
        void onLoginSuccessful();
    }
}
