package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.interactors.base.Callback;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.domain.model.Project;

import java.util.List;

import javax.inject.Inject;


public class ProjectListInteractor extends AbstractInteractor<Callback<List<Project>>> {


    @Inject
    public ProjectListInteractor(Executor threadExecutor,
                                 MainThread mainThread
    ) {
        super(threadExecutor, mainThread);
    }


    @Override
    public void run() {
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
}
