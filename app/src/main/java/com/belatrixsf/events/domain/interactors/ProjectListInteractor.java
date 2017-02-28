package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.model.Project;

import java.util.List;

import javax.inject.Inject;


public class ProjectListInteractor extends AbstractInteractor<List<Project>,ProjectListInteractor.Params> {


    @Inject
    public ProjectListInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params ...params) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onResult(Project.getDummyData());
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
