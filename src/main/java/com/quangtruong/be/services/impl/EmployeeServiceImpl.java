package com.quangtruong.be.services.impl;

import com.quangtruong.be.entities.Employee;
import com.quangtruong.be.repositories.EmployeeRepository;
import com.quangtruong.be.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        // Mã hóa mật khẩu trước khi lưu
        employee.setPasswordHash(passwordEncoder.encode(employee.getPasswordHash()));
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setFullName(updatedEmployee.getFullName());
                    employee.setEmail(updatedEmployee.getEmail());
                    employee.setPhone(updatedEmployee.getPhone());
                    employee.setRole(updatedEmployee.getRole());
                    employee.setCreatedAt(updatedEmployee.getCreatedAt());
                    employee.setUpdatedAt(updatedEmployee.getUpdatedAt());
                    employee.setActive(updatedEmployee.isActive());

                    // Cẩn thận khi cập nhật mật khẩu, cần có cơ chế bảo mật phù hợp
                    if (updatedEmployee.getPasswordHash() != null && !updatedEmployee.getPasswordHash().isEmpty()) {
                        employee.setPasswordHash(passwordEncoder.encode(updatedEmployee.getPasswordHash()));
                    }

                    return employeeRepository.save(employee);
                })
                .orElse(null);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}