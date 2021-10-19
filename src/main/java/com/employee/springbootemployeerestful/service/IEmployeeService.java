package com.employee.springbootemployeerestful.service;

import com.employee.springbootemployeerestful.entity.Employee;

import java.util.List;

public interface IEmployeeService {
    Iterable<Employee> getAll();

    List<Employee> getEmployeeListByName(String name);

    Employee getOneEmployee(String id);

    Employee getOneEmployeeByEmpNo(String empNo);

    void addEmployee(Employee emp);

    void updateEmployee(Employee emp);

    void deleteEmployee(String id);
}
