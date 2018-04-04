package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Device;
import com.belatrix.events.domain.repository.DeviceRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class UpdateDeviceInteractor extends AbstractInteractor {

    public interface CallBack {
        void onSuccess(Device device);
        void onError();
    }

    @Inject
    DeviceRepository deviceRepository;

    @Inject
    public UpdateDeviceInteractor() {
    }

    public void updateDevice(final UpdateDeviceInteractor.CallBack callBack, Params params) {
        Integer deviceId = params.deviceId;
        Integer cityId = params.cityId;
        disposable = deviceRepository.update(deviceId, cityId).subscribe(new Consumer<Device>() {
            @Override
            public void accept(Device device) throws Exception {
                callBack.onSuccess(device);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callBack.onError();
            }
        });
    }

    public static final class Params {
        Integer deviceId;
        Integer cityId;

        public Params(Integer deviceId, Integer cityId) {
            this.deviceId = deviceId;
            this.cityId = cityId;
        }

        public static UpdateDeviceInteractor.Params forUpdateDevice(Integer deviceId, Integer cityId) {
            return new UpdateDeviceInteractor.Params(deviceId,cityId);
        }
    }

}
