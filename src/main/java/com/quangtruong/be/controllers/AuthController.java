package com.quangtruong.be.controllers;

import com.quangtruong.be.config.JwtProvider;
import com.quangtruong.be.dto.CustomerDTO;
import com.quangtruong.be.entities.Customer;
import com.quangtruong.be.entities.Employee;
import com.quangtruong.be.request.CreateCustomerRequest;
import com.quangtruong.be.request.LoginRequest;
import com.quangtruong.be.response.AuthResponse;
import com.quangtruong.be.services.CustomerService;
import com.quangtruong.be.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody CreateCustomerRequest request) throws Exception {
        CustomerDTO isEmailExist = customerService.getCustomerByEmail(request.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Email is already used with another account");
        }
        Customer createdUser = new Customer();
        createdUser.setEmail(request.getEmail());
        createdUser.setFullName(request.getFullName());
        createdUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        createdUser.setActive(true);
        Customer savedUser = customerService.saveCustomer(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPasswordHash());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse("success", token);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerService.loadUserByUsername(email);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @PostMapping("/employee/signup")
    public ResponseEntity<AuthResponse> createEmployeeHandler(@Valid @RequestBody Employee employee) throws Exception {

        String email = employee.getEmail();
        Employee isEmailExist = employeeService.findByEmail(email);

        // Kiểm tra xem email đã tồn tại chưa
        if (isEmailExist != null) {
            throw new Exception("Email đã được sử dụng với tài khoản khác");
        }

        // Tạo người dùng mới
        Employee createdEmployee = new Employee();
        createdEmployee.setEmail(email);
        createdEmployee.setFullName(employee.getFullName());
        createdEmployee.setPasswordHash(employee.getPasswordHash());
        createdEmployee.setRole("ROLE_EMPLOYEE"); // Hoặc "ROLE_ADMIN" tùy theo yêu cầu
        createdEmployee.setActive(true);
        Employee savedEmployee = employeeService.saveEmployee(createdEmployee, passwordEncoder); // Truyền passwordEncoder vào đây

        // Tạo JWT token
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedEmployee.getEmail(), savedEmployee.getPasswordHash());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        // Tạo AuthResponse
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Đăng ký thành công");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/employee/login")
    public ResponseEntity<AuthResponse> loginEmployeeHandler(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticateEmployee(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Đăng nhập thành công");

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticateEmployee(String email, String password) {
        UserDetails userDetails = employeeService.loadUserByUsername(email);

        if (userDetails == null) {
            throw new BadCredentialsException("Tên đăng nhập không hợp lệ");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Mật khẩu không hợp lệ");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}