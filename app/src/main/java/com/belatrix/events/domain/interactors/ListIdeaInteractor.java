package com.belatrix.events.domain.interactors;

import android.text.TextUtils;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class ListIdeaInteractor extends AbstractInteractor {

    private final EventRepository mEventRepository;

    @Inject
    ListIdeaInteractor(EventRepository eventRepository) {
        this.mEventRepository = eventRepository;
    }

    public void getIdeaList(final ListIdeaInteractor.CallBack callback, Params params) {
        final Params p = params;
        disposable = mEventRepository.interactionList(p.eventId).subscribe(new Consumer<List<Project>>() {
            @Override
            public void accept(final List<Project> projects) throws Exception {
                for (Project project : projects) {
                    if (TextUtils.isEmpty(project.getDescription())) {
                        String[] aux = project.getTitle().split(" - ");
                        project.setTitle(aux[0]);
                        project.setDescription(aux[1]);
                    }
                }
                callback.onSuccess(projects);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError();
            }
        });
    }

    public interface CallBack {
        void onSuccess(List<Project> result);

        void onError();
    }

    public static final class Params {
        int eventId;

        public Params(int eventId) {
            this.eventId = eventId;
        }

        public static ListIdeaInteractor.Params forEvent(int eventId) {
            return new ListIdeaInteractor.Params(eventId);
        }

    }

}
