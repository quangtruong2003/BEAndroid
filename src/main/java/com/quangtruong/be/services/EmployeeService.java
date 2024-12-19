package com.quangtruong.be.services;

import com.quangtruong.be.entities.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long id);
    Employee saveEmployee(Employee employee);
    void deleteEmployee(Long id);
    Employee findByEmail(String email);
    Employee updateEmployee(Long id, Employee employee);
}