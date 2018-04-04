package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Device;
import com.belatrix.events.domain.repository.DeviceRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class RegisterDeviceInteractor extends AbstractInteractor{

    public interface CallBack {
        void onSuccess(Device device);

        void onError();
    }

    @Inject
    DeviceRepository deviceRepository;

    @Inject
    public RegisterDeviceInteractor() {
    }

    public void registerDevice(final RegisterDeviceInteractor.CallBack callback, Params params) {
        String token = params.token;
        Integer city = params.cityId;
         disposable = deviceRepository.register(token, city).subscribe(new Consumer<Device>() {
            @Override
            public void accept(Device device) throws Exception {
                callback.onSuccess(device);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError();
            }
        });
    }

    public static final class Params {
        String token;
        Integer cityId;

        public Params(String token, Integer cityId) {
            this.token = token;
        }

        public static RegisterDeviceInteractor.Params forRegisterDevice(String token, Integer cityId) {
            return new RegisterDeviceInteractor.Params(token, cityId);
        }

    }

}
