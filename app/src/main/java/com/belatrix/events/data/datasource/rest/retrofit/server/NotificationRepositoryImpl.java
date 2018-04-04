package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.rest.retrofit.api.NotificationAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.domain.model.Notification;
import com.belatrix.events.domain.repository.NotificationRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by raulrashuaman on 4/12/17.
 */

public class NotificationRepositoryImpl extends BaseRepository implements NotificationRepository {

    NotificationAPI notificationAPI;

    public NotificationRepositoryImpl(NotificationAPI notificationAPI) {
        this.notificationAPI = notificationAPI;
    }

    @Override
    public Observable<List<Notification>> notificationList(Integer cityId) {
       return subscribeOn(notificationAPI.notificationList(cityId));
    }
}
