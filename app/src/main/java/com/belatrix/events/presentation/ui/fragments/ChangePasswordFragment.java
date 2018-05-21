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
import com.belatrix.events.presentation.presenters.ChangePasswordPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.Validator;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordFragment extends BelatrixBaseFragment implements ChangePasswordPresenter.View {

    private final static String ARGS_TOKEN = "args_token";

    @BindView(R.id.et_old_password)
    EditText etOldPassword;

    @BindView(R.id.et_new_password)
    EditText etNewPassword;

    @BindView(R.id.et_confirm_new_password)
    EditText etConfirmNewPassword;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindString(R.string.hint_password)
    String hintPassword;

    @Inject
    ChangePasswordPresenter changePasswordPresenter;

    @Inject
    Validator mValidator;

    private LoginFragment.LoginCallback mLoginCallback;

    private String mToken;

    public static Fragment create(Context context, String token) {
        Bundle args = new Bundle();
        args.putString(ARGS_TOKEN, token);
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
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        changePasswordPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.change_password));
        Bundle args = getArguments();
        if (args != null) {
            mToken = args.getString(ARGS_TOKEN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @OnClick(R.id.bt_change_password)
    public void onClickChangePasswordEvent() {
        String oldPassword, newPassword, confirmNewPassword;
        if (etConfirmNewPassword != null && getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etConfirmNewPassword.getWindowToken(), 0);
            }
            etOldPassword.setError(null);
            etNewPassword.setError(null);
            etConfirmNewPassword.setError(null);
        }

        tvError.setVisibility(View.GONE);
        oldPassword = validateStringInput(etOldPassword, hintPassword);
        newPassword = validateStringInput(etNewPassword, hintPassword);
        confirmNewPassword = validateConfirmPasswordInput(etConfirmNewPassword, newPassword, hintPassword);
        if (!oldPassword.isEmpty() && !newPassword.isEmpty() && !confirmNewPassword.isEmpty()) {
            changePasswordPresenter.changePassword(mToken, oldPassword, newPassword);
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

    private String validateConfirmPasswordInput(EditText editText, String passwordValue, String field) {
        String confirmPassword = validateStringInput(editText, field);
        if (!passwordValue.equals(confirmPassword)) {
            editText.setError(getString(R.string.error_passwords_not_equals));
            return "";
        }
        return passwordValue;
    }

    private String validateStringInput(EditText editText, String field) {
        String value, error;
        value = editText.getText().toString();
        error = mValidator.validateStringField(field, value);
        if (!error.isEmpty()) {
            editText.setError(error);
            return "";
        }
        return value;
    }
}
