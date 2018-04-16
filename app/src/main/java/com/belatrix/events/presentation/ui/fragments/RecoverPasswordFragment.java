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
import com.belatrix.events.presentation.presenters.RecoverPasswordPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RecoverPasswordFragment extends BelatrixBaseFragment implements RecoverPasswordPresenter.View {

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.tv_error)
    TextView tvError;

    @Inject
    RecoverPasswordPresenter recoverPasswordPresenter;

    @Inject
    Validator mValidator;

    public static Fragment newInstance(Context context) {
        Bundle args = new Bundle();
        return Fragment.instantiate(context, RecoverPasswordFragment.class.getName(), args);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        recoverPasswordPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        tilEmail.setErrorEnabled(true);
        setTitle(getString(R.string.recover_password));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recover_password, container, false);
    }

    @OnClick(R.id.bt_recover_password)
    public void onClickRecoverEvent() {
        String email = "", emailError = "";
        tilEmail.setError("");
        tvError.setVisibility(View.GONE);
        if (tilEmail.getEditText() != null) {
            email = tilEmail.getEditText().getText().toString();
            emailError = mValidator.validateEmailField(getString(R.string.hint_email), email);
            if (!emailError.isEmpty()) {
                tilEmail.setError(emailError);
            }
        }
        if (emailError.isEmpty()) {
            recoverPasswordPresenter.recoverPassword(email);
        }
    }

    @Override
    public void onRecoverPasswordSuccessful() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onRecoverPasswordError() {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(R.string.error_server_recover_password);
    }
}
