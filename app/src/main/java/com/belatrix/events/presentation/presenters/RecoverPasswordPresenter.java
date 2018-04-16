package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.RecoverPasswordInteractor;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import javax.inject.Inject;

public class RecoverPasswordPresenter extends BelatrixBasePresenter<RecoverPasswordPresenter.View> implements RecoverPasswordInteractor.Callback {

    private final RecoverPasswordInteractor mRecoverPasswordInteractor;

    @Inject
    public RecoverPasswordPresenter(RecoverPasswordInteractor recoverPasswordInteractor) {
        this.mRecoverPasswordInteractor = recoverPasswordInteractor;
    }

    @Override
    public void cancelRequests() {
        mRecoverPasswordInteractor.cancel();
    }

    public void recoverPassword(String email) {
        view.showProgressDialog();
        mRecoverPasswordInteractor.recoverPassword(this, email);
    }

    @Override
    public void onRecoverSuccessful() {
        if(view == null){
            return;
        }
        view.dismissProgressDialog();
        view.onRecoverPasswordSuccessful();
    }

    @Override
    public void onRecoverError() {
        if(view == null){
            return;
        }
        view.dismissProgressDialog();
        view.onRecoverPasswordError();
    }

    public interface View extends BelatrixBaseView {
        void onRecoverPasswordSuccessful();

        void onRecoverPasswordError();
    }
}
