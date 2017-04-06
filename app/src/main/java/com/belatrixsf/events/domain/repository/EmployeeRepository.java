package com.belatrixsf.events.domain.repository;

import com.belatrixsf.events.data.datasource.ServerCallback;
import com.belatrixsf.events.domain.model.Employee;
import com.belatrixsf.events.domain.model.Project;

import java.util.List;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EmployeeRepository {

    void employee(String employeeId, ServerCallback<Employee> callBack);
}
