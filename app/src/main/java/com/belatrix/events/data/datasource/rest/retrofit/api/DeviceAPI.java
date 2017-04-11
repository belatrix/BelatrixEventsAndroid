package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.domain.model.Device;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface DeviceAPI {
    @POST("device/register/")
    Call<Device> register(@Body JsonObject body);
}
