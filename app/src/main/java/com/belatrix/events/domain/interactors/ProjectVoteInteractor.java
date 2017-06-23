package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.EventRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class ProjectVoteInteractor extends AbstractInteractor {

    public interface CallBack {
        void onSuccess(Project result);

        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public ProjectVoteInteractor() {
    }

    public void actionVote(final ProjectVoteInteractor.CallBack callback, Params params) {
        int interactionId = params.projectId;
        disposable = eventRepository.interactionVote(interactionId).subscribe(new Consumer<Project>() {
            @Override
            public void accept(Project project) throws Exception {
                callback.onSuccess(project);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError();
            }
        });
    }

    public static final class Params {
        int projectId;

        public Params(int projectId) {
            this.projectId = projectId;
        }

        public static ProjectVoteInteractor.Params forProject(int projectId) {
            return new ProjectVoteInteractor.Params(projectId);
        }

    }

}
