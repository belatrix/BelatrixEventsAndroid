package com.belatrix.events.domain.repository;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.model.Notification;

import java.util.List;

/**
 * Created by raulrashuaman on 4/12/17.
 */

public interface NotificationRepository {

    void notificationList(Integer cityId, ServerCallback<List<Notification>> callBack);
}
