package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.domain.interactors.EventFeaturedListInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBaseView;

import java.util.List;

import javax.inject.Inject;


public class HomeFeaturedPresenter extends BelatrixBasePresenter<HomeFeaturedPresenter.View> implements Callback<List<Event>> {

    public interface View extends BelatrixBaseView {
        void showEventList (List<Event> list);
    }


   EventFeaturedListInteractor interactor;

    @Inject
    public HomeFeaturedPresenter(EventFeaturedListInteractor interactor) {

        this.interactor = interactor;
    }


    public void getEventFeaturedList() {
        view.showProgressIndicator();
        interactor.execute(this);
    }


    @Override
    public void onResult(List<Event> list) {
        view.hideProgressIndicator();
        view.showEventList(list);

    }

    @Override
    public void onError(String errorMessage) {
        view.hideProgressIndicator();
    }


    @Override
    public void cancelRequests() {
        interactor.cancel();
    }
}
