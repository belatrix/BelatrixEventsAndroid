package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.data.datasource.rest.retrofit.api.NotificationAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.domain.model.Notification;
import com.belatrix.events.domain.repository.NotificationRepository;

import java.util.List;

import retrofit2.Call;

/**
 * Created by raulrashuaman on 4/12/17.
 */

public class NotificationRepositoryImpl extends BaseRepository implements NotificationRepository {

    NotificationAPI notificationAPI;

    public NotificationRepositoryImpl(NotificationAPI notificationAPI) {
        this.notificationAPI = notificationAPI;
    }

    @Override
    public void notificationList(Integer cityId, ServerCallback<List<Notification>> callBack) {
        Call<List<Notification>> call = notificationAPI.notificationList(cityId);
        executeRequest(callBack, call);
    }
}
