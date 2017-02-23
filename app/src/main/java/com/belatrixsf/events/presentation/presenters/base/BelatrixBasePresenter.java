package com.belatrixsf.events.presentation.presenters.base;


import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;

/**
 * This is a base class for all presenters which are communicating with interactors. This base class will hold a
 * reference to the Executor and MainThread objAbstractPresenterects that are needed for running interactors in a background thread.
 */
public abstract class BelatrixBasePresenter<T extends BelatrixBaseView>  {
    protected Executor   mExecutor;
    protected MainThread mMainThread;
    protected T view;


    public void setView(T view) {
        this.view = view;
    }

    public BelatrixBasePresenter() {
    }

    protected String getString(int resId) {
        return view.getContext().getString(resId);
    }

    protected void showError(int resId) {
        showError(getString(resId));
    }

    protected void showError(String message) {
        view.showError(message);
    }




}
