package com.belatrix.events.presentation.ui.fragments;

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
import com.belatrix.events.presentation.presenters.CreateAccountPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.Validator;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class CreateAccountFragment extends BelatrixBaseFragment implements CreateAccountPresenter.View {

    @BindView(R.id.til_user)
    TextInputLayout tilUser;

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.til_name)
    TextInputLayout tilName;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.til_confirm_password)
    TextInputLayout tilConfirmPassword;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindString(R.string.hint_user)
    String hintUser;

    @BindString(R.string.hint_email)
    String hintEmail;

    @BindString(R.string.hint_name)
    String hintName;

    @BindString(R.string.hint_password)
    String hintPassword;

    @Inject
    CreateAccountPresenter createAccountPresenter;

    @Inject
    Validator mValidator;

    public static Fragment newInstance(Context context) {
        Bundle args = new Bundle();
        return Fragment.instantiate(context, CreateAccountFragment.class.getName(), args);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        createAccountPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.create_account));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @OnClick(R.id.bt_create_account)
    public void onClickCreateAccountEvent() {
        String user, email, name, password, confirmPassword;
        tilUser.setErrorEnabled(false);
        tilEmail.setErrorEnabled(false);
        tilName.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
        tilConfirmPassword.setErrorEnabled(false);
        tvError.setVisibility(View.GONE);
        user = validateStringInput(tilUser, hintUser);
        email = validateEmailInput(tilEmail, hintEmail);
        name = validateStringInput(tilName, hintName);
        password = validateStringInput(tilPassword, hintPassword);
        confirmPassword = validateConfirmPasswordInput(tilConfirmPassword, password, hintPassword);
        if (!user.isEmpty() && !email.isEmpty() && !name.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            createAccountPresenter.createAccount(user, email, name, password);
        }
    }

    private String validateStringInput(TextInputLayout textInputLayout, String field) {
        String value = "", error;
        if (textInputLayout.getEditText() != null) {
            value = textInputLayout.getEditText().getText().toString();
            error = mValidator.validateStringField(field, value);
            if (!error.isEmpty()) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(error);
                return "";
            }
        }
        return value;
    }

    private String validateConfirmPasswordInput(TextInputLayout inputConfirmPassword, String passwordValue, String field) {
        String confirmPassword = validateStringInput(inputConfirmPassword, field);
        if (!passwordValue.equals(confirmPassword)) {
            inputConfirmPassword.setErrorEnabled(true);
            inputConfirmPassword.setError(getString(R.string.error_passwords_not_equals));
            return "";
        }
        return passwordValue;
    }

    private String validateEmailInput(TextInputLayout textInputLayout, String field) {
        String value = "", error;
        if (textInputLayout.getEditText() != null) {
            value = textInputLayout.getEditText().getText().toString();
            error = mValidator.validateEmailField(field, value);
            if (!error.isEmpty()) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(error);
                return "";
            }
        }
        return value;
    }

    @Override
    public void onCreateAccountSuccessful() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onCreateAccountFailed() {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(R.string.error_server_create_account);
    }
}