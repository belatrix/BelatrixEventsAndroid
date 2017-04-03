package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;

import javax.inject.Inject;


public class ProjectVoteInteractor extends AbstractInteractor<ProjectVoteInteractor.CallBack, ProjectVoteInteractor.Params> {

    public interface CallBack {
        void onSuccess(Boolean result);
        void onError();
    }

    @Inject
    public ProjectVoteInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params ...params) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(true);
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

        public static ProjectVoteInteractor.Params forProject(int projectId){
            return new ProjectVoteInteractor.Params(projectId);
        }

    }

}
