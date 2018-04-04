package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Employee;
import com.belatrix.events.domain.repository.EmployeeRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class GetPersonByQRInteractor extends AbstractInteractor {


    public interface CallBack {
        void onEmployeeSuccess(Employee response);
        void onEmployeeNotFound();
        void onError();
    }

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    public GetPersonByQRInteractor() {
    }

    public void getPersonByQR(final GetPersonByQRInteractor.CallBack callback, Params params) {
        String qrCode = params.qrCode;
        disposable = employeeRepository.employee(qrCode).subscribe(new Consumer<Employee>() {
            @Override
            public void accept(Employee employee) throws Exception {
                callback.onEmployeeSuccess(employee);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
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
