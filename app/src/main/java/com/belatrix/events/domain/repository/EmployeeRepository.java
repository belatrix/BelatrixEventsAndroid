package com.belatrix.events.domain.repository;

import com.belatrix.events.data.datasource.ServerCallback;
import com.belatrix.events.domain.model.Employee;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EmployeeRepository {

    void employee(String employeeId, ServerCallback<Employee> callBack);
}
