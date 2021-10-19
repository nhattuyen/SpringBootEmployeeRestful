package com.employee.springbootemployeerestful.repository;

import com.employee.springbootemployeerestful.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface IEmployeeRepository extends CrudRepository<Employee, Integer> {

    Employee findEmployeesById(String id);
    Employee findEmployeesByEmpNo(String empNo);
    List<Employee> findAllByFullnameContaining(String fullname);
    List<Employee> findAllByIdAndFullname(String id, String fullname);
    List<Employee> findAllByEmpNoIsNotNull();
    List<Employee> findEmployeesByHireDateGreaterThan(Date hiredate);
    boolean existsByEmpNo(String empno);
    int deleteById(String id);
}
