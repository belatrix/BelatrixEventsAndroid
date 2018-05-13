package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.rest.retrofit.response.IdeaCreateResponse;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.repository.IdeaRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class CreateIdeaInteractor extends AbstractInteractor {

    private final IdeaRepository mIdeaRepository;

    @Inject
    CreateIdeaInteractor(IdeaRepository ideaRepository) {
        this.mIdeaRepository = ideaRepository;
    }

    public void createIdea(final Callback callback, String token, int authorId, int eventId, String title, String description) {
        disposable = mIdeaRepository.createIdea(token, authorId, eventId, title, description).subscribe(new Consumer<IdeaCreateResponse>() {
            @Override
            public void accept(IdeaCreateResponse ideaCreateResponse) throws Exception {
                callback.onIdeaCreated();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError();
            }
        });
    }

    public interface Callback {
        void onIdeaCreated();

        void onError();
    }
}
