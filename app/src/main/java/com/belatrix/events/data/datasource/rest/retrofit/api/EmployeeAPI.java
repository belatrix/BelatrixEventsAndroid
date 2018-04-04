package com.belatrix.events.data.datasource.rest.retrofit.api;

import com.belatrix.events.domain.model.Employee;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EmployeeAPI {
    @GET("employee/{employee_id}/")
    Observable<Employee> employee(@Path("employee_id") String employeeId);
}
