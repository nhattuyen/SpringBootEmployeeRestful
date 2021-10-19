package com.employee.springbootemployeerestful.controller;

import com.employee.springbootemployeerestful.entity.Employee;
import com.employee.springbootemployeerestful.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeRESTController {

    @Autowired
    private EmployeeServiceImpl employeeService;
    /*
    Employee Data Repository
     */

    @RequestMapping("/")
    //@CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public String Welcome() {
        return "This is RestEmployee example";
    }

    //@CrossOrigin//(origins = "http://localhost:8080", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET})
    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<Employee> getEmployees() {
        List<Employee> employeeList = (List<Employee>) employeeService.getAll();
        if(employeeList.isEmpty()){
            return null;
        }
        return employeeList;
    }

    //@CrossOrigin
    @RequestMapping(value = "/employee/{empNo}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public Employee getEmployee(@PathVariable("empNo") String empNo) {
        return employeeService.getOneEmployeeByEmpNo(empNo);
    }

    //@CrossOrigin
    @RequestMapping(value = "/employee", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Employee addEmployee(@RequestBody Employee emp) {
        Employee e = employeeService.getOneEmployeeByEmpNo(emp.getEmpNo());
        if(e != null) {
            System.out.println("The employee " + emp.getFullname() + " is already existed.");
            return null;
        } else {
            employeeService.addEmployee(emp);
            System.out.println("The new employee "+emp.getFullname()+" has been created successfully on server.");
            return emp;
        }
    }

    //@CrossOrigin
    @RequestMapping(value = "/employee", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Employee updateEmployee(@RequestBody Employee employee) {
        Employee e = employeeService.getOneEmployeeByEmpNo(employee.getEmpNo());
        if(e != null) {
            employeeService.updateEmployee(employee);
            System.out.println("The employee " + employee.getFullname() + " has been updated.");
            return employee;
        }
        else {
            System.out.println("The employee "+employee.getFullname()+" can not be founded.");
            return null;
        }
    }

    //@CrossOrigin
    @RequestMapping(value = "/employee/{empNo}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public void deleteEmployee(@PathVariable("empNo") String empNo) {
        Employee e = employeeService.getOneEmployeeByEmpNo(empNo);
        if(e != null) {
            employeeService.deleteEmployee(e.getId());
            System.out.println("The employee "+empNo+" has been deleted.");
        } else {
            System.out.println("The employee "+empNo+" can not be found.");
        }

    }
}
