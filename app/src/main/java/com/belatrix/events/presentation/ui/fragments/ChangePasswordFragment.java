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
import com.belatrix.events.presentation.presenters.ChangePasswordPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.Validator;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordFragment extends BelatrixBaseFragment implements ChangePasswordPresenter.View {

    private final static String ARGS_USER_ID = "args_user_id";

    @BindView(R.id.til_old_password)
    TextInputLayout tilOldPassword;

    @BindView(R.id.til_new_password)
    TextInputLayout tilNewPassword;

    @BindView(R.id.til_confirm_new_password)
    TextInputLayout tilConfirmNewPassword;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindString(R.string.hint_password)
    String hintPassword;

    @Inject
    ChangePasswordPresenter changePasswordPresenter;

    @Inject
    Validator mValidator;

    private LoginFragment.LoginCallback mLoginCallback;

    private int userId;

    public static Fragment newInstance(Context context, int userId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_USER_ID, userId);
        return Fragment.instantiate(context, ChangePasswordFragment.class.getName(), args);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LoginFragment.LoginCallback) {
            mLoginCallback = (LoginFragment.LoginCallback) activity;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.LoginCallback) {
            mLoginCallback = (LoginFragment.LoginCallback) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            userId = args.getInt(ARGS_USER_ID);
        }
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        changePasswordPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.change_password));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @OnClick(R.id.bt_change_password)
    public void onClickChangePasswordEvent() {
        String oldPassword, newPassword, confirmNewPassword;
        tilOldPassword.setErrorEnabled(false);
        tilNewPassword.setErrorEnabled(false);
        tilConfirmNewPassword.setErrorEnabled(false);
        tvError.setVisibility(View.GONE);
        oldPassword = validateStringInput(tilOldPassword, hintPassword);
        newPassword = validateStringInput(tilNewPassword, hintPassword);
        confirmNewPassword = validateConfirmPasswordInput(tilConfirmNewPassword, newPassword, hintPassword);
        if (!oldPassword.isEmpty() && !newPassword.isEmpty() && !confirmNewPassword.isEmpty()) {
            changePasswordPresenter.changePassword(userId, oldPassword, newPassword);
        }
    }

    @Override
    public void onChangePasswordSuccessful() {
        mLoginCallback.onLoginSuccessful();
    }

    @Override
    public void onChangePasswordError() {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(R.string.error_server_change_password);
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
}
