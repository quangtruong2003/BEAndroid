package com.quangtruong.be.services;

import com.quangtruong.be.dto.CustomerDTO;
import com.quangtruong.be.entities.Customer;
import com.quangtruong.be.request.CreateCustomerRequest;
import com.quangtruong.be.request.UpdateCustomerRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CustomerService extends UserDetailsService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO createCustomer(CreateCustomerRequest customer);
    CustomerDTO updateCustomer(Long id, UpdateCustomerRequest customer);
    void deleteCustomer(Long id);
    CustomerDTO getCustomerByEmail(String email);
    Customer saveCustomer(Customer customer);
}