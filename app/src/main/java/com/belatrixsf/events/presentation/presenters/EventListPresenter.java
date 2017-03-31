package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.domain.interactors.GetEventListInteractor;
import com.belatrixsf.events.domain.interactors.GetEventFeaturedInteractor;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBaseView;

import java.util.List;

import javax.inject.Inject;


public class EventListPresenter extends BelatrixBasePresenter<EventListPresenter.View> {


    public interface View extends BelatrixBaseView {
        void showEventList(List<Event> list);
    }

    GetEventListInteractor getEventListInteractor;

    String eventType;
    String eventTitle;


    public String getEventType() {
        return eventType;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    @Inject
    public EventListPresenter(GetEventListInteractor interactor) {
        this.getEventListInteractor = interactor;
    }

    public void setParams(String eventType, String eventTitle){
        this.eventTitle = eventTitle;
        this.eventType = eventType;
    }


    public void actionGetEventList() {
        getEventListInteractor.execute(new GetEventListInteractor.CallBack() {
            @Override
            public void onSuccess(List<Event> result) {
                view.hideProgressIndicator();
                view.showEventList(result);
            }

            @Override
            public void onError() {

            }
        });
    }


    @Override
    public void cancelRequests() {
        getEventListInteractor.cancel();
    }
}
