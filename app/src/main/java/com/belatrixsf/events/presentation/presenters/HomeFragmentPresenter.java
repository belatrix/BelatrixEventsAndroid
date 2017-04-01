package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.domain.interactors.GetEventFeaturedInteractor;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBaseView;

import javax.inject.Inject;


public class HomeFragmentPresenter extends BelatrixBasePresenter<HomeFragmentPresenter.View> {


    public interface View extends BelatrixBaseView {
        void showHomeEvent(String urlImage);
    }

    GetEventFeaturedInteractor getEventFeaturedInteractor;

    String eventType;
    String eventTitle;


    public String getEventType() {
        return eventType;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    @Inject
    public HomeFragmentPresenter(GetEventFeaturedInteractor interactor) {
        this.getEventFeaturedInteractor = interactor;
    }

    public void setParams(String eventType, String eventTitle){
        this.eventTitle = eventTitle;
        this.eventType = eventType;
    }


    public void actionLoadHomeEvent() {
        getEventFeaturedInteractor.execute(new GetEventFeaturedInteractor.CallBack() {
            @Override
            public void onSuccess(String urlImage) {
                view.showHomeEvent(urlImage);
            }

            @Override
            public void onError() {

            }
        });
    }


    @Override
    public void cancelRequests() {
        getEventFeaturedInteractor.cancel();
    }
}
