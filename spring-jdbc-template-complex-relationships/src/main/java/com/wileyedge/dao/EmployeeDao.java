package com.wileyedge.dao;

import java.util.List;

import com.wileyedge.entity.Employee;

public interface EmployeeDao {
    
    List<Employee> getAllEmployees();

    Employee getEmployeeById(int id);

    Employee addEmployee(Employee employee);

    void updateEmployee(Employee employee);
}
