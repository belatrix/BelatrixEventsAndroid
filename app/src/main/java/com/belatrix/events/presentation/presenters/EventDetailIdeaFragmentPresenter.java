package com.belatrix.events.presentation.presenters;

import com.belatrix.events.R;
import com.belatrix.events.domain.interactors.ListIdeaInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.cache.Cache;

import java.util.List;

import javax.inject.Inject;

public class EventDetailIdeaFragmentPresenter extends BelatrixBasePresenter<EventDetailIdeaFragmentPresenter.View>{

    public interface View extends BelatrixBaseView {
        void showProjectList(List<Project> list);
        void showEmptyView();
        void onError(String errorMessage);
    }

    private final ListIdeaInteractor interactor;

    @Inject
    Cache cache;

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Inject
    EventDetailIdeaFragmentPresenter(ListIdeaInteractor interactor) {
        this.interactor = interactor;
    }

    public void getIdeaList(final int eventId) {
        view.showProgressIndicator();
        interactor.getIdeaList(new ListIdeaInteractor.CallBack() {
            @Override
            public void onSuccess(List<Project> result) {
                view.hideProgressIndicator();
                if (result.isEmpty()){
                    view.showEmptyView();
                } else {
                    view.showProjectList(result);
                }
            }

            @Override
            public void onError() {
                view.hideProgressIndicator();
                view.onError(view.getContext().getString(R.string.dialog_title_error));
            }
        }, ListIdeaInteractor.Params.forEvent(eventId));
    }

    @Override
    public void cancelRequests() {
        interactor.cancel();
    }

    public void updateFirstTime() {
        cache.updateStartAppFlag();
    }

    public boolean isFirstTime(){
        return cache.isFirstTimeStartApp();
    }

}
