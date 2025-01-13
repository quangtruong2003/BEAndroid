package com.quangtruong.be.services.impl;

import com.quangtruong.be.config.JwtProvider;
import com.quangtruong.be.dto.CustomerDTO;
import com.quangtruong.be.entities.Customer;
import com.quangtruong.be.repositories.CustomerRepository;
import com.quangtruong.be.request.CreateCustomerRequest;
import com.quangtruong.be.request.UpdateCustomerRequest;
import com.quangtruong.be.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

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
        customer.setActive(request.isActive());

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
                if(!Objects.equals(customer.getEmail(), request.getEmail())){
                    Customer existingCustomer = customerRepository.findByEmail(request.getEmail());
                    if (existingCustomer != null && !existingCustomer.getCustomerId().equals(customer.getCustomerId())) {
                        throw new RuntimeException("Email already exists for another customer");
                    }
                }
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
            if (request.getActive() != null) {
                customer.setActive(request.getActive());
            }
            return convertToDto(customerRepository.save(customer));
        }
        return null;
    }

    @Override
    public CustomerDTO findById(Long id) throws Exception {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new Exception("Customer not found with id: " + id));
        return convertToDto(customer);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception{
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new Exception("Customer not found with id: " + id);
        }
        customerRepository.delete(customer);
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

        return new User(customer.getEmail(), customer.getPasswordHash(), authorities);
    }



    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findUserProfileByJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);
        if (email == null) {
            throw new BadCredentialsException("Invalid token");
        }
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new Exception("User not found with email " + email);
        }
        return customer;
    }

    @Override
    public CustomerDTO updateCustomerStatus(Long id, boolean status) throws Exception {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new Exception("Customer not found with id: " + id));
        customer.setActive(status);
        customer.setUpdatedAt(LocalDateTime.now());
        return convertToDto(customerRepository.save(customer));
    }
}