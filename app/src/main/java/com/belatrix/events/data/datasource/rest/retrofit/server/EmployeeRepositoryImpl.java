package com.belatrix.events.data.datasource.rest.retrofit.server;

import com.belatrix.events.data.datasource.rest.retrofit.api.EmployeeAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.domain.model.Employee;
import com.belatrix.events.domain.repository.EmployeeRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by diegoveloper on 3/31/17.
 */

public class EmployeeRepositoryImpl extends BaseRepository implements EmployeeRepository {

    EmployeeAPI employeeAPI;

    public EmployeeRepositoryImpl(EmployeeAPI employeeAPI) {
        this.employeeAPI = employeeAPI;
    }

    @Override
    public Observable<Employee> employee(String employeId) {
        return subscribeOn(employeeAPI.employee(employeId));
    }
}
