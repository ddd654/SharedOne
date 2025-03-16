package com.project.tobe.repository;

import com.project.tobe.entity.Employee;

import java.util.List;

public interface EmployeeCustomRepository {
    List<Employee> findEmployeesByAuthorityOrdered();
}
