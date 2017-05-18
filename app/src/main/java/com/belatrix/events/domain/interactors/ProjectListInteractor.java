package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.executor.Executor;
import com.belatrix.events.domain.executor.MainThread;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.List;

import javax.inject.Inject;


public class ProjectListInteractor extends AbstractInteractor<ProjectListInteractor.CallBack,ProjectListInteractor.Params> {

    public interface CallBack {
        void onSuccess(List<Project> result);
        void onError();
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    public ProjectListInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }

    @Override
    public void run(Params ...params) {
        Params p = params[0];
        eventRepository.interactionList(p.eventId, new ServerCallback<List<Project>>() {
            @Override
            public void onSuccess(final List<Project> response) {
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
        int eventId;

        public Params(int eventId) {
            this.eventId = eventId;
        }

        public static ProjectListInteractor.Params forEvent(int eventId){
            return new ProjectListInteractor.Params(eventId);
        }

    }

}
