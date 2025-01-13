package com.quangtruong.be.services.impl;

import com.quangtruong.be.dto.EmployeeDTO;
import com.quangtruong.be.entities.Employee;
import com.quangtruong.be.repositories.EmployeeRepository;
import com.quangtruong.be.request.UpdateEmployeeRequest;
import com.quangtruong.be.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

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

    // Sửa lại: Bỏ passwordEncoder
    @Override
    public Employee saveEmployee(Employee employee, PasswordEncoder passwordEncoder) {
        return employeeRepository.save(employee);
    }

    // Sửa lại: Sử dụng UpdateEmployeeRequest
    @Override
    public Employee updateEmployee(Long id, UpdateEmployeeRequest updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    if (updatedEmployee.getEmail() != null && !Objects.equals(employee.getEmail(), updatedEmployee.getEmail())) {
                        Employee existingEmployee = employeeRepository.findByEmail(updatedEmployee.getEmail());
                        if (existingEmployee != null && !existingEmployee.getEmployeeId().equals(employee.getEmployeeId())) {
                            throw new RuntimeException("Email already exists for another employee");
                        }
                        employee.setEmail(updatedEmployee.getEmail());
                    }
                    if (updatedEmployee.getFullName() != null) {
                        employee.setFullName(updatedEmployee.getFullName());
                    }
                    if (updatedEmployee.getPhone() != null) {
                        employee.setPhone(updatedEmployee.getPhone());
                    }
                    if (updatedEmployee.getActive() != null) {
                        employee.setActive(updatedEmployee.getActive());
                    }

                    // Mã hóa mật khẩu nếu được cập nhật
                    if (updatedEmployee.getPassword() != null && !updatedEmployee.getPassword().isEmpty()) {
                        employee.setPasswordHash(passwordEncoder.encode(updatedEmployee.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user by username: {}", username);

        Employee employee = employeeRepository.findByEmail(username);
        if (employee == null) {
            logger.debug("Employee not found for username: {}", username);
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        logger.debug("Employee found: {}", employee.getEmail());
        logger.debug("Employee ID: {}", employee.getEmployeeId());

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(employee.getEmail(), employee.getPasswordHash(), authorities);
    }

    public EmployeeDTO convertToDto(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setActive(employee.isActive());
        return dto;
    }

    public List<EmployeeDTO> toDtoList(List<Employee> employees){
        return employees.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}