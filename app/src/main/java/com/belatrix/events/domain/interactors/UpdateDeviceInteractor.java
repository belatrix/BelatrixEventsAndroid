package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.executor.Executor;
import com.belatrix.events.domain.executor.MainThread;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Device;
import com.belatrix.events.domain.repository.DeviceRepository;

import javax.inject.Inject;


public class UpdateDeviceInteractor extends AbstractInteractor<UpdateDeviceInteractor.CallBack, UpdateDeviceInteractor.Params> {

    public interface CallBack {
        void onSuccess(Device device);
        void onError();
    }

    @Inject
    DeviceRepository deviceRepository;

    @Inject
    public UpdateDeviceInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params... params) {
        Integer deviceId = params[0].deviceId;
        Integer cityId = params[0].cityId;
        deviceRepository.update(deviceId, cityId, new ServerCallback<Device>() {
            @Override
            public void onSuccess(final Device result) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
            }

            @Override
            public void onFail(int statusCode, String errorMessage) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError();
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError();
                    }
                });
            }
        });
    }

    @Override
    public void onError(Exception e) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                callback.onError();
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
