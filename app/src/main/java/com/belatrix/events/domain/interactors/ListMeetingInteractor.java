package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Meeting;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ListMeetingInteractor extends AbstractInteractor {

    private final EventRepository mEventRepository;

    @Inject
    ListMeetingInteractor(EventRepository eventRepository) {
        this.mEventRepository = eventRepository;
    }

    public void listMeetings(final Callback callback, String token) {
        disposable = mEventRepository.listMeetings(token).subscribe(new Consumer<List<Meeting>>() {
            @Override
            public void accept(List<Meeting> meetings) throws Exception {
                callback.onListEventsSuccessful(meetings);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onListEventsError();
            }
        });
    }

    public interface Callback {
        void onListEventsSuccessful(List<Meeting> lst);

        void onListEventsError();
    }
}
