package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.domain.model.Device;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface DeviceAPI {
    @POST("device/register/android/")
    Observable<Device> register(@Body JsonObject body);
    @PATCH("device/{device_id}/update/city/")
    Observable<Device> update(@Path("device_id") Integer deviceId, @Body JsonObject body);
}
