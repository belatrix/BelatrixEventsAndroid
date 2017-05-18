package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.executor.Executor;
import com.belatrix.events.domain.executor.MainThread;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Device;
import com.belatrix.events.domain.repository.DeviceRepository;

import javax.inject.Inject;


public class RegisterDeviceInteractor extends AbstractInteractor<RegisterDeviceInteractor.CallBack, RegisterDeviceInteractor.Params> {

    public interface CallBack {
        void onSuccess(Device device);
        void onError();
    }

    @Inject
    DeviceRepository deviceRepository;

    @Inject
    public RegisterDeviceInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params... params) {
        String token = params[0].token;
        Integer city = params[0].cityId;
        deviceRepository.register(token,city,  new ServerCallback<Device>() {
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
        String token;
        Integer cityId;

        public Params(String token, Integer cityId) {
            this.token = token;
        }

        public static RegisterDeviceInteractor.Params forRegisterDevice(String token, Integer cityId) {
            return new RegisterDeviceInteractor.Params(token,cityId);
        }

    }

}
