package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.RegisterAssistanceInteractor;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

public class ScannerPresenter extends BelatrixBasePresenter<ScannerPresenter.View> implements RegisterAssistanceInteractor.Callback {

    private final RegisterAssistanceInteractor mRegisterAssistanceInteractor;
    private final AccountUtils mAccountUtils;

    @Inject
    ScannerPresenter(RegisterAssistanceInteractor registerAssistanceInteractor, AccountUtils accountUtils) {
        this.mRegisterAssistanceInteractor = registerAssistanceInteractor;
        this.mAccountUtils = accountUtils;
    }

    @Override
    public void cancelRequests() {
        mRegisterAssistanceInteractor.cancel();
    }

    public void registerAssistant(int meetingId, String email) {
        mRegisterAssistanceInteractor.registerAssistance(ScannerPresenter.this, mAccountUtils.getToken(), meetingId, email);
    }

    @Override
    public void onRegistrationSuccessful(User user) {
        view.onRegistrationSuccessful();
    }

    @Override
    public void onRegistrationFailed() {
        view.onRegistrationFailed();
    }

    public interface View extends BelatrixBaseView {
        void onRegistrationSuccessful();

        void onRegistrationFailed();
    }
}
