package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.executor.Executor;
import com.belatrix.events.domain.executor.MainThread;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Notification;
import com.belatrix.events.domain.repository.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by raulrashuaman on 4/11/17.
 */

public class GetNotificationListInteractor extends AbstractInteractor<GetNotificationListInteractor.CallBack, GetNotificationListInteractor.Params> {

    public interface CallBack {
        void onSuccess(List<Notification> result);
        void onError();
    }

    @Inject
    NotificationRepository notificationRepository;

    @Inject
    public GetNotificationListInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }

    @Override
    public void run(Params...params) {
        Params p = params[0];
        notificationRepository.notificationList(p.cityId, new ServerCallback<List<Notification>>() {
            @Override
            public void onSuccess(final List<Notification> response) {
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
        private Integer cityId;

        private Params(Integer cityId) {
            this.cityId = cityId;
        }

        public static Params forCity(Integer cityId) {
            return new Params(cityId);
        }
    }
}
