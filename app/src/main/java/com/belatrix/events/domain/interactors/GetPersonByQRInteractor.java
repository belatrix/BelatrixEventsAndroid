package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.executor.Executor;
import com.belatrix.events.domain.executor.MainThread;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Employee;
import com.belatrix.events.domain.repository.EmployeeRepository;

import javax.inject.Inject;

public class GetPersonByQRInteractor extends AbstractInteractor<GetPersonByQRInteractor.CallBack, GetPersonByQRInteractor.Params> {


    public interface CallBack {
        void onEmployeeSuccess(Employee response);

        void onEmployeeNotFound();

        void onError();
    }

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    public GetPersonByQRInteractor(Executor mThreadExecutor, MainThread mMainThread) {
        super(mThreadExecutor, mMainThread);
    }


    @Override
    public void run(Params... params) {
        String qrCode = params[0].qrCode;
        employeeRepository.employee(qrCode, new ServerCallback<Employee>() {
            @Override
            public void onSuccess(final Employee response) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onEmployeeSuccess(response);
                    }
                });
            }

            @Override
            public void onFail(int statusCode, String errorMessage) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onEmployeeNotFound();
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

    public static class Params {

        private String qrCode;

        private Params(String qrCode) {
            this.qrCode = qrCode;
        }

        public static Params forQR(String qrCode) {
            return new Params(qrCode);
        }
    }
}
