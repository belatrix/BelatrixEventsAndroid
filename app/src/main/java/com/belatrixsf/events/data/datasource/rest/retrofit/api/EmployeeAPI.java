package com.belatrixsf.events.data.datasource.rest.retrofit.api;

import com.belatrixsf.events.domain.model.Employee;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EmployeeAPI {
    @GET("employee/{employee_id}/")
    Call<Employee> employee(@Path("employee_id") String employeeId);
}
