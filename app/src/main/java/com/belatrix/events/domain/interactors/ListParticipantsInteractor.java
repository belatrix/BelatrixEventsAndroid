package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.rest.retrofit.response.ParticipantsResponse;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Author;
import com.belatrix.events.domain.repository.IdeaRepository;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ListParticipantsInteractor extends AbstractInteractor {

    private final IdeaRepository mIdeaRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    ListParticipantsInteractor(IdeaRepository ideaRepository, AccountUtils accountUtils) {
        this.mIdeaRepository = ideaRepository;
        this.mAccountUtils = accountUtils;
    }

    public void getParticipantsListById(final Callback callback, int ideaId) {
        if (mAccountUtils.existsAccount()) {
            disposable = mIdeaRepository.listParticipantsWithToken(mAccountUtils.getToken(), ideaId).subscribe(new Consumer<ParticipantsResponse>() {
                @Override
                public void accept(ParticipantsResponse participantsResponse) throws Exception {
                    List<Author> lst = new ArrayList<>();
                    for (ParticipantsResponse.TeamMembers teamMembers : participantsResponse.getLstTeamMembers()) {
                        lst.add(teamMembers.getAuthor());
                    }
                    callback.onParticipantsSuccess(participantsResponse.isRegistered(), lst);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    callback.onParticipantsError();
                }
            });
        } else {
            disposable = mIdeaRepository.listParticipants(ideaId).subscribe(new Consumer<ParticipantsResponse>() {
                @Override
                public void accept(ParticipantsResponse participantsResponse) throws Exception {
                    List<Author> lst = new ArrayList<>();
                    for (ParticipantsResponse.TeamMembers teamMembers : participantsResponse.getLstTeamMembers()) {
                        lst.add(teamMembers.getAuthor());
                    }
                    callback.onParticipantsSuccess(participantsResponse.isRegistered(), lst);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    callback.onParticipantsError();
                }
            });
        }
    }

    public interface Callback {
        void onParticipantsSuccess(boolean isRegistered, List<Author> result);

        void onParticipantsError();
    }
}
