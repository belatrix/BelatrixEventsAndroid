package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.GetEventListInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.cache.Cache;

import java.util.List;

import javax.inject.Inject;


public class EventListFragmentPresenter extends BelatrixBasePresenter<EventListFragmentPresenter.View> {


    public interface View extends BelatrixBaseView {
        void showEventList(List<Event> list);
        void showEmptyView();
        void showMoreEventsButton(boolean show);
    }

    GetEventListInteractor getEventListInteractor;

    String eventType;
    String eventTitle;
    @Inject
    Cache cache;

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
        view.showMoreEventsButton(false);
        getEventListInteractor.getEventList(new GetEventListInteractor.CallBack() {
            @Override
            public void onSuccess(List<Event> result) {
                view.hideProgressIndicator();
                if (result!= null && !result.isEmpty()) {
                    view.showEventList(result);
                    if(result.size()>2){
                        view.showMoreEventsButton(true);
                    }
                } else {
                    view.showMoreEventsButton(false);
                    view.showEmptyView();
                }
            }

            @Override
            public void onError() {
                view.hideProgressIndicator();
                view.showMoreEventsButton(false);
                view.showEmptyView();
            }
        }, GetEventListInteractor.Params.forEventType(eventType,cache.getCity()));
    }


    @Override
    public void cancelRequests() {
        getEventListInteractor.cancel();
    }
}
