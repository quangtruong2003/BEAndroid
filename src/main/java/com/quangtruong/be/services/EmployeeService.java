package com.quangtruong.be.services;

import com.quangtruong.be.dto.EmployeeDTO;
import com.quangtruong.be.entities.Employee;
import com.quangtruong.be.request.UpdateEmployeeRequest;
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
    // Sửa lại method signature:
    Employee updateEmployee(Long id, UpdateEmployeeRequest employee);
    EmployeeDTO convertToDto(Employee employee);
}