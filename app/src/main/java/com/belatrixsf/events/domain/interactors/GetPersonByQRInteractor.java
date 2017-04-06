package com.belatrixsf.events.domain.interactors;

import com.belatrixsf.events.data.datasource.ServerCallback;
import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.interactors.base.AbstractInteractor;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.domain.repository.EventRepository;

import javax.inject.Inject;

public class GetPersonByQRInteractor extends AbstractInteractor<GetPersonByQRInteractor.CallBack, GetPersonByQRInteractor.Params> {


    public interface CallBack {
        void onSuccess();
        void onError();
    }

    @Inject
    public GetPersonByQRInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params... params) {
        String qrCode = params[0].qrCode;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess();
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

    public static class Params {

        private String qrCode;

        private Params(String qrCode) {
            this.qrCode = qrCode;
        }

        public static Params forQR(String qrCode){
            return new Params(qrCode);
        }
    }
}
