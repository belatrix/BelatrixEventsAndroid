package com.belatrix.events.domain.repository;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.model.Device;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface DeviceRepository {

    void register(String deviceCode, ServerCallback<Device> callBack);
}
