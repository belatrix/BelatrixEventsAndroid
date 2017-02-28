package com.belatrixsf.events.presentation.presenters.base;

public abstract class BelatrixBasePresenter<T extends BelatrixBaseView>  {
    protected T view;

    public BelatrixBasePresenter(T view){
        this.view = view;
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

    public abstract void cancelRequests();


}
