package com.belatrix.events.domain.repository;

import com.belatrix.events.domain.model.Device;

import io.reactivex.Observable;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface DeviceRepository {

    Observable<Device> register(String deviceCode, Integer cityId);
    Observable<Device> update(Integer deviceId, Integer cityId);
}
