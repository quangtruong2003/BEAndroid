package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.EmployeeDTO;
import com.quangtruong.be.entities.Employee;
import com.quangtruong.be.request.UpdateEmployeeRequest;
import com.quangtruong.be.services.EmployeeService;
import com.quangtruong.be.services.impl.EmployeeServiceImpl;
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

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOs = employeeServiceImpl.toDtoList(employees);
        return new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) throws Exception {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new Exception("Employee not found with id: " + id));
        EmployeeDTO employeeDTO = employeeServiceImpl.convertToDto(employee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody Employee employee) {
        // Sửa lại: Truyền passwordEncoder vào hàm saveEmployee
        Employee savedEmployee = employeeService.saveEmployee(employee, null);
        EmployeeDTO employeeDTO = employeeServiceImpl.convertToDto(savedEmployee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody UpdateEmployeeRequest request) {
        Employee updatedEmployee = employeeService.updateEmployee(id, request); // Sửa lại: Truyền request
        return new ResponseEntity<>(employeeServiceImpl.convertToDto(updatedEmployee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeDTO> findByEmail(@PathVariable String email){
        Employee employee = employeeService.findByEmail(email);
        if (employee != null) {
            return new ResponseEntity<>(employeeServiceImpl.convertToDto(employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}