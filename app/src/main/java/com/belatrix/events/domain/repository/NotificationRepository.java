package com.belatrix.events.domain.repository;

import com.belatrix.events.domain.model.Notification;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by raulrashuaman on 4/12/17.
 */

public interface NotificationRepository {

    Observable<List<Notification>> notificationList(Integer cityId);
}
