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
    void deleteCustomer(Long id) throws Exception;
    CustomerDTO getCustomerByEmail(String email);

    Customer saveCustomer(Customer createdUser);

    Customer findUserProfileByJwt(String jwt) throws Exception;

    // Thêm 2 method này vào interface
    CustomerDTO findById(Long id) throws Exception;
    CustomerDTO updateCustomerStatus(Long id, boolean status) throws Exception;
}