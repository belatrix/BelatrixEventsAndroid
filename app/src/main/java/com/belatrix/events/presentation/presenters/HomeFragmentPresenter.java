package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.GetEventFeaturedInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.cache.Cache;

import javax.inject.Inject;


public class HomeFragmentPresenter extends BelatrixBasePresenter<HomeFragmentPresenter.View> {


    public interface View extends BelatrixBaseView {
        void showHomeEvent(Event event);
    }

    GetEventFeaturedInteractor getEventFeaturedInteractor;

    String eventType;
    String eventTitle;

    Event event;
    @Inject
    Cache cache;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

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
            public void onSuccess(Event event) {
                view.showHomeEvent(event);
            }

            @Override
            public void onError() {

            }
        }, GetEventFeaturedInteractor.Params.forCity(cache.getCity()));
    }


    @Override
    public void cancelRequests() {
        getEventFeaturedInteractor.cancel();
    }
}
