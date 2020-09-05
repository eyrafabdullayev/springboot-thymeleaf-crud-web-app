package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    void saveEmployee(Employee employee);

    Employee getEmployeeById(long id);

    void deleteEmployee(long id);

    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
