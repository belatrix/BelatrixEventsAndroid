package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.rest.retrofit.api.DeviceAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.domain.model.Device;
import com.belatrix.events.domain.repository.DeviceRepository;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by diegoveloper on 3/31/17.
 */

public class DeviceRepositoryImpl extends BaseRepository implements DeviceRepository {

    DeviceAPI deviceAPI;

    public DeviceRepositoryImpl(DeviceAPI deviceAPI) {
        this.deviceAPI = deviceAPI;
    }

    @Override
    public Observable<Device> register(String deviceCode, Integer cityId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("device_code",deviceCode);
        if (cityId != null){
            jsonObject.addProperty("city",cityId);
        }
        return deviceAPI.register(jsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Device> update(Integer deviceId, Integer cityId) {
        JsonObject jsonObject = new JsonObject();
        if (cityId != null){
            jsonObject.addProperty("city",cityId);
        }
        return deviceAPI.update(deviceId,jsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
