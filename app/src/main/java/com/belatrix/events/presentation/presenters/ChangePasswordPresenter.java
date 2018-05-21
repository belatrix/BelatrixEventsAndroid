package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.ChangePasswordInteractor;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import javax.inject.Inject;

public class ChangePasswordPresenter extends BelatrixBasePresenter<ChangePasswordPresenter.View> implements ChangePasswordInteractor.Callback {

    private final ChangePasswordInteractor mChangePasswordInteractor;

    @Inject
    ChangePasswordPresenter(ChangePasswordInteractor changePasswordInteractor) {
        this.mChangePasswordInteractor = changePasswordInteractor;
    }

    @Override
    public void cancelRequests() {
        mChangePasswordInteractor.cancel();
    }

    public void changePassword(String token, String oldPassword, String newPassword) {
        view.showProgressDialog();
        mChangePasswordInteractor.changePassword(this, token, oldPassword, newPassword);
    }

    @Override
    public void onChangeSuccessful() {
        if (view == null) {
            return;
        }
        view.dismissProgressDialog();
        view.onChangePasswordSuccessful();
    }

    @Override
    public void onChangeError() {
        if (view == null) {
            return;
        }
        view.dismissProgressDialog();
        view.onChangePasswordError();
    }

    public interface View extends BelatrixBaseView {
        void onChangePasswordSuccessful();

        void onChangePasswordError();
    }
}

