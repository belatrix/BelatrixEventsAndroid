package com.belatrixsf.events.data.datasource.rest.retrofit.server;

import com.belatrixsf.events.data.datasource.ServerCallback;
import com.belatrixsf.events.data.datasource.rest.retrofit.api.EmployeeAPI;
import com.belatrixsf.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrixsf.events.domain.model.Employee;
import com.belatrixsf.events.domain.repository.EmployeeRepository;


import retrofit2.Call;

/**
 * Created by diegoveloper on 3/31/17.
 */

public class EmployeeRepositoryImpl extends BaseRepository implements EmployeeRepository {

    EmployeeAPI employeeAPI;

    public EmployeeRepositoryImpl(EmployeeAPI employeeAPI) {
        this.employeeAPI = employeeAPI;
    }

    @Override
    public void employee(String employeId, ServerCallback<Employee> callBack) {
        Call<Employee> call = employeeAPI.employee(employeId);
        executeRequest(callBack, call);
    }
}
