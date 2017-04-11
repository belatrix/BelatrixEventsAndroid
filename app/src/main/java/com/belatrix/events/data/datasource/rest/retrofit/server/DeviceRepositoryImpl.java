package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.data.datasource.rest.retrofit.api.DeviceAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.domain.model.Device;
import com.belatrix.events.domain.repository.DeviceRepository;
import com.google.gson.JsonObject;

import retrofit2.Call;

/**
 * Created by diegoveloper on 3/31/17.
 */

public class DeviceRepositoryImpl extends BaseRepository implements DeviceRepository {

    DeviceAPI deviceAPI;

    public DeviceRepositoryImpl(DeviceAPI deviceAPI) {
        this.deviceAPI = deviceAPI;
    }

    @Override
    public void register(String deviceCode, ServerCallback<Device> callBack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("device_code",deviceCode);
        Call<Device> call = deviceAPI.register(jsonObject);
        executeRequest(callBack, call);
    }
}
