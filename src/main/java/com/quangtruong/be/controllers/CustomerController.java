package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.CustomerDTO;
import com.quangtruong.be.entities.Customer;
import com.quangtruong.be.request.CreateCustomerRequest;
import com.quangtruong.be.request.UpdateCustomerRequest;
import com.quangtruong.be.response.CustomerResponse;
import com.quangtruong.be.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerResponse> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        CustomerResponse response = new CustomerResponse("success", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        if (customerDTO != null) {
            CustomerResponse response = new CustomerResponse("success", customerDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomerResponse response = new CustomerResponse("Customer not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerDTO createdCustomer = customerService.createCustomer(request);
        CustomerResponse response = new CustomerResponse("Customer created successfully", createdCustomer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest request) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, request);
        if (updatedCustomer != null) {
            CustomerResponse response = new CustomerResponse("Customer updated successfully", updatedCustomer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomerResponse response = new CustomerResponse("Customer not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        CustomerResponse response = new CustomerResponse("Customer deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@PathVariable String email) {
        CustomerDTO customerDTO = customerService.getCustomerByEmail(email);
        if (customerDTO != null) {
            CustomerResponse response = new CustomerResponse("success", customerDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomerResponse response = new CustomerResponse("Customer not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}