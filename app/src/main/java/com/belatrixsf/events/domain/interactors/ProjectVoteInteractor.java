package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.data.datasource.ServerCallback;
import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.model.Project;
import com.belatrixsf.events.domain.repository.EventRepository;

import javax.inject.Inject;


public class ProjectVoteInteractor extends AbstractInteractor<ProjectVoteInteractor.CallBack, ProjectVoteInteractor.Params> {

    public interface CallBack {
        void onSuccess(Project result);

        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public ProjectVoteInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params... params) {
        int interactionId = params[0].projectId;
        eventRepository.interactionVote(interactionId, new ServerCallback<Project>() {
            @Override
            public void onSuccess(final Project response) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(response);
                    }
                });
            }

            @Override
            public void onFail(int statusCode, String errorMessage) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError();
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError();
                    }
                });
            }
        });


    }

    @Override
    public void onError(Exception e) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
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
