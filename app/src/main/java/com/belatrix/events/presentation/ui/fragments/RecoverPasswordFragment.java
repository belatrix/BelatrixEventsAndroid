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
import com.belatrix.events.presentation.presenters.RecoverPasswordPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.Validator;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class RecoverPasswordFragment extends BelatrixBaseFragment implements RecoverPasswordPresenter.View {

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindString(R.string.hint_email)
    String hintEmail;

    @Inject
    RecoverPasswordPresenter recoverPasswordPresenter;

    @Inject
    Validator mValidator;

    private RecoverPasswordCallback mRecoverPasswordCallback;

    public static Fragment newInstance(Context context) {
        Bundle args = new Bundle();
        return Fragment.instantiate(context, RecoverPasswordFragment.class.getName(), args);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof RecoverPasswordCallback) {
            mRecoverPasswordCallback = (RecoverPasswordCallback) activity;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecoverPasswordCallback) {
            mRecoverPasswordCallback = (RecoverPasswordCallback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRecoverPasswordCallback = null;
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        recoverPasswordPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.recover_password));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recover_password, container, false);
    }

    @OnClick(R.id.bt_recover_password)
    public void onClickRecoverEvent() {
        String email, emailError;
        if (etEmail != null && getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
            }
            etEmail.setError(null);
        }
        tvError.setVisibility(View.GONE);
        email = etEmail.getText().toString();
        emailError = mValidator.validateEmailField(hintEmail, email);
        if (!emailError.isEmpty()) {
            etEmail.setError(emailError);
        }
        if (emailError.isEmpty()) {
            recoverPasswordPresenter.recoverPassword(email);
        }
    }

    @Override
    public void onRecoverPasswordSuccessful() {
        if (mRecoverPasswordCallback != null) {
            mRecoverPasswordCallback.onRecoverPassword();
        }
    }

    @Override
    public void onRecoverPasswordError() {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(R.string.error_server_recover_password);
    }

    public interface RecoverPasswordCallback {
        void onRecoverPassword();
    }
}
