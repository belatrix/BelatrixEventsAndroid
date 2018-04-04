package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class ProjectListInteractor extends AbstractInteractor{

    public interface CallBack {
        void onSuccess(List<Project> result);
        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public ProjectListInteractor() {
    }

    public void getInteractionList(final ProjectListInteractor.CallBack callback, Params params) {
        Params p = params;
        disposable = eventRepository.interactionList(p.eventId).subscribe(new Consumer<List<Project>>() {
            @Override
            public void accept(List<Project> projects) throws Exception {
                callback.onSuccess(projects);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError();
            }
        });
    }

    public static final class Params {
        int eventId;

        public Params(int eventId) {
            this.eventId = eventId;
        }

        public static ProjectListInteractor.Params forEvent(int eventId){
            return new ProjectListInteractor.Params(eventId);
        }

    }

}
