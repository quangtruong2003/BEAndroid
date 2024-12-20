package com.quangtruong.be.services;

import com.quangtruong.be.entities.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public interface EmployeeService extends UserDetailsService {
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long id);
    Employee saveEmployee(Employee employee, PasswordEncoder passwordEncoder);
    void deleteEmployee(Long id);
    Employee findByEmail(String email);
    Employee updateEmployee(Long id, Employee employee);
}