package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Vote;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class ListVotesInteractor extends AbstractInteractor {

    private final EventRepository mEventRepository;

    @Inject
    ListVotesInteractor(EventRepository eventRepository) {
        this.mEventRepository = eventRepository;
    }

    public void getInteractionList(final ListVotesInteractor.CallBack callback, Params params) {
        final Params p = params;
        disposable = mEventRepository.voteList(p.eventId).subscribe(new Consumer<List<Vote>>() {
            @Override
            public void accept(final List<Vote> votes) throws Exception {
                for (Vote vote : votes) {
                    vote.setTitle(vote.getTitle().split(" - ")[0]);
                }
                Collections.sort(votes, new Comparator<Vote>() {
                    @Override
                    public int compare(Vote o1, Vote o2) {
                        return o1.getVotes() < o2.getVotes() ? -1 : o1.getVotes() > o2.getVotes() ? 1 : 0;
                    }
                });
                callback.onSuccess(votes);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError();
            }
        });
    }

    public interface CallBack {
        void onSuccess(List<Vote> result);

        void onError();
    }

    public static final class Params {
        int eventId;

        public Params(int eventId) {
            this.eventId = eventId;
        }

        public static ListVotesInteractor.Params forEvent(int eventId) {
            return new ListVotesInteractor.Params(eventId);
        }

    }

}
