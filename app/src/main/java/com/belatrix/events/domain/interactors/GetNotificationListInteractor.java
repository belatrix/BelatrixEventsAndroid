package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Notification;
import com.belatrix.events.domain.repository.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by raulrashuaman on 4/11/17.
 */

public class GetNotificationListInteractor extends AbstractInteractor{
    public interface CallBack {
        void onSuccess(List<Notification> result);
        void onError();
    }

    @Inject
    NotificationRepository notificationRepository;

    @Inject
    public GetNotificationListInteractor() {
    }

    public void getNotificationList(final GetNotificationListInteractor.CallBack callback, Params params) {
        Params p = params;
        disposable = notificationRepository.notificationList(p.cityId).subscribe(new Consumer<List<Notification>>() {
            @Override
            public void accept(List<Notification> notifications) throws Exception {
                callback.onSuccess(notifications);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
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
