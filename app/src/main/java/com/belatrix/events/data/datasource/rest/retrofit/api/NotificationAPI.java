package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.domain.model.Notification;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by raulrashuaman on 4/12/17.
 */

public interface NotificationAPI {
    @GET("notifications/all/")
    Observable<List<Notification>> notificationList(@Query("city") Integer cityId);
}
