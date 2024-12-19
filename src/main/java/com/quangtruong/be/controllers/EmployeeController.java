package com.quangtruong.be.controllers;

import com.quangtruong.be.entities.Employee;
import com.quangtruong.be.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee employee1 = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(employee1, HttpStatus.OK);
//        return employeeService.getEmployeeById(id)
//                .map(existingEmployee -> {
//                    existingEmployee.setEmployeeId(id);
//                    // Cẩn thận khi cho phép update password qua API, cần có cơ chế bảo mật phù hợp
//                    // ở đây, tạm thời bỏ qua update passwordHash
//                    // employee.setPasswordHash(existingEmployee.getPasswordHash());
//                    Employee updatedEmployee = employeeService.saveEmployee(existingEmployee);
//                    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
//                })
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> findByEmail(@PathVariable String email){
        Employee employee = employeeService.findByEmail(email);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}