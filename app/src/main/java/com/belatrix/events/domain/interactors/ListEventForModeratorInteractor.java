package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.repository.EventRepository;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ListEventForModeratorInteractor extends AbstractInteractor {

    private final EventRepository mEventRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    public ListEventForModeratorInteractor(EventRepository eventRepository, AccountUtils accountUtils) {
        this.mEventRepository = eventRepository;
        this.mAccountUtils = accountUtils;
    }

    public void listEventForModerator(final Callback callback, int cityId) {
        disposable = mEventRepository.listEvent(mAccountUtils.getToken(), cityId).subscribe(new Consumer<List<Event>>() {
            @Override
            public void accept(List<Event> events) throws Exception {
                callback.onListEventLoaded(events);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onListEventError();
            }
        });
    }

    public interface Callback {
        void onListEventLoaded(List<Event> lstEvent);

        void onListEventError();
    }
}
