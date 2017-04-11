package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.executor.Executor;
import com.belatrix.events.domain.executor.MainThread;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Device;
import com.belatrix.events.domain.repository.DeviceRepository;

import javax.inject.Inject;


public class RegisterFCMInteractor extends AbstractInteractor<RegisterFCMInteractor.CallBack, RegisterFCMInteractor.Params> {

    public interface CallBack {
        void onSuccess(Device device);
        void onError();
    }

    @Inject
    DeviceRepository deviceRepository;

    @Inject
    public RegisterFCMInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params... params) {
        String token = params[0].token;
        deviceRepository.register(token, new ServerCallback<Device>() {
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

        public Params(String token) {
            this.token = token;
        }

        public static RegisterFCMInteractor.Params forRegisterFCM(String token) {
            return new RegisterFCMInteractor.Params(token);
        }

    }

}
