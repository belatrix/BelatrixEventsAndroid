package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.model.Project;

import java.util.Collections;
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

        Params p = params[0];
        final List<Project> list = Project.getDummyData();
        if (p.orderRequired) {
            Collections.sort(list);
            list.get(0).setHighest(true);
        }
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onResult(list);
            }
        });
    }

    public static final class Params {
        int eventId;
        boolean orderRequired;

        public Params(int eventId, boolean orderRequired) {
            this.eventId = eventId;
            this.orderRequired = orderRequired;
        }

        public static ProjectListInteractor.Params forEvent(int eventId, boolean orderRequired){
            return new ProjectListInteractor.Params(eventId,orderRequired);
        }

    }

}
