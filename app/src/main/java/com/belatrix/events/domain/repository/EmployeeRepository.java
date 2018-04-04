package com.belatrix.events.domain.repository;

import com.belatrix.events.domain.model.Employee;

import io.reactivex.Observable;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EmployeeRepository {

    Observable<Employee> employee(String employeeId);
}
