package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.ListEventForModeratorInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.cache.Cache;

import java.util.List;

import javax.inject.Inject;

public class ManageIdeaPresenter extends BelatrixBasePresenter<ManageIdeaPresenter.View> implements ListEventForModeratorInteractor.Callback {

    private final ListEventForModeratorInteractor mListEventForModeratorInteractor;
    private final Cache mCache;

    @Inject
    public ManageIdeaPresenter(ListEventForModeratorInteractor listEventForModeratorInteractor, Cache cache) {
        mListEventForModeratorInteractor = listEventForModeratorInteractor;
        mCache = cache;
    }

    @Override
    public void cancelRequests() {
        mListEventForModeratorInteractor.cancel();
    }

    public void listEvent() {
        mListEventForModeratorInteractor.listEventForModerator(ManageIdeaPresenter.this, mCache.getCity());
    }

    @Override
    public void onListEventLoaded(List<Event> lstEvent) {
        view.onListEventLoaded(lstEvent);
    }

    @Override
    public void onListEventError() {
        view.onListEventError();
    }

    public interface View extends BelatrixBaseView {
        void onListEventLoaded(List<Event> lstEvent);

        void onListEventError();
    }
}
