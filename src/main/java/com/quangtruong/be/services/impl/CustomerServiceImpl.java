package com.quangtruong.be.services.impl;

import com.quangtruong.be.dto.CustomerDTO;
import com.quangtruong.be.entities.Customer;
import com.quangtruong.be.repositories.CustomerRepository;
import com.quangtruong.be.request.CreateCustomerRequest;
import com.quangtruong.be.request.UpdateCustomerRequest;
import com.quangtruong.be.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public CustomerDTO createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        customer.setAddress(request.getAddress());
        customer.setActive(true);

        return convertToDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            if (request.getFullName() != null) {
                customer.setFullName(request.getFullName());
            }
            if (request.getEmail() != null) {
                customer.setEmail(request.getEmail());
            }
            if (request.getPhone() != null) {
                customer.setPhone(request.getPhone());
            }
            if (request.getAddress() != null) {
                customer.setAddress(request.getAddress());
            }
            if (request.getUpdatedAt() != null) {
                customer.setUpdatedAt(request.getUpdatedAt());
            }
            if (request.getIsActive() != null) {
                customer.setActive(request.getIsActive());
            }
            return convertToDto(customerRepository.save(customer));
        }
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        return convertToDto(customer);
    }

    private CustomerDTO convertToDto(Customer customer) {
        if (customer == null) return null;
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        dto.setActive(customer.isActive());
        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        // Thêm logic để lấy role từ Customer entity (nếu có)
        // Ví dụ: authorities.add(new SimpleGrantedAuthority(customer.getRole()));
        // Hoặc set cứng role là ROLE_CUSTOMER:
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        return new User(customer.getEmail(), customer.getPasswordHash(), authorities);
    }



    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}