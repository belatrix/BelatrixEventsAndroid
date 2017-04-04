package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.domain.interactors.GetEventListInteractor;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBaseView;

import java.util.List;

import javax.inject.Inject;


public class EventListFragmentPresenter extends BelatrixBasePresenter<EventListFragmentPresenter.View> {


    public interface View extends BelatrixBaseView {
        void showEventList(List<Event> list);
        void showEmptyView();
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
    public EventListFragmentPresenter(GetEventListInteractor interactor) {
        this.getEventListInteractor = interactor;
    }

    public void setParams(String eventType, String eventTitle){
        this.eventTitle = eventTitle;
        this.eventType = eventType;
    }


    public void actionGetEventList() {
        view.showProgressIndicator();
        getEventListInteractor.execute(new GetEventListInteractor.CallBack() {
            @Override
            public void onSuccess(List<Event> result) {
                view.hideProgressIndicator();
                if (result!= null && !result.isEmpty()) {
                    view.showEventList(result);
                } else {
                    view.showEmptyView();
                }
            }

            @Override
            public void onError() {
                view.hideProgressIndicator();
                view.showEmptyView();
            }
        }, GetEventListInteractor.Params.forEventType(eventType));
    }


    @Override
    public void cancelRequests() {
        getEventListInteractor.cancel();
    }
}
