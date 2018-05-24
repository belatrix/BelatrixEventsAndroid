package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.repository.EventRepository;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;


public class ProjectVoteInteractor extends AbstractInteractor {

    private final EventRepository mEventRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    ProjectVoteInteractor(EventRepository eventRepository, AccountUtils accountUtils) {
        mEventRepository = eventRepository;
        mAccountUtils = accountUtils;
    }

    public void actionVote(final ProjectVoteInteractor.Callback callback, int eventId, int ideaId) {
        disposable = mEventRepository.voteForIdea(mAccountUtils.getToken(), eventId, ideaId).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) throws Exception {
                callback.onSuccess();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError();
            }
        });
    }

    public interface Callback {
        void onSuccess();

        void onError();
    }

}
