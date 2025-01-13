package com.quangtruong.be.controllers;

import com.quangtruong.be.config.JwtProvider;
import com.quangtruong.be.dto.CustomerDTO;
import com.quangtruong.be.entities.Customer;
import com.quangtruong.be.entities.Employee;
import com.quangtruong.be.request.CreateCustomerRequest;
import com.quangtruong.be.request.CreateEmployeeRequest;
import com.quangtruong.be.request.LoginRequest;
import com.quangtruong.be.response.AuthResponse;
import com.quangtruong.be.services.CustomerService;
import com.quangtruong.be.services.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody CreateCustomerRequest request) throws Exception {
        CustomerDTO isEmailExist = customerService.getCustomerByEmail(request.getEmail());
        if (isEmailExist != null) {
            logger.warn("Attempt to register with existing email: {}", request.getEmail());
            throw new Exception("Email is already used with another account");
        }
        Customer createdUser = new Customer();
        createdUser.setEmail(request.getEmail());
        createdUser.setFullName(request.getFullName());
        createdUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        createdUser.setActive(true);
        Customer savedUser = customerService.saveCustomer(createdUser);

        // Không cần set Authentication ở đây
        // Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPasswordHash());
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        // Bỏ phần này, Spring Security tự xử lý
        // String jwt = jwtProvider.generateToken(authentication);

        // Sửa message để khỏi lộ passwordHash
        AuthResponse authResponse = new AuthResponse();
        // authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");

        logger.info("New user registered: {}", savedUser.getEmail());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        logger.debug("Login request: email={}", email);

        // Bỏ phần authenticate thủ công, để Spring Security xử lý
        // Authentication authentication = authenticate(email, loginRequest.getPassword());
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        // String token = jwtProvider.generateToken(authentication);
        // Thay vào đó, sử dụng AuthenticationManager để xác thực
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse("success", token);
            logger.debug("Login response: {}", authResponse);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // Xử lý lỗi xác thực
            logger.error("Authentication failed for email: {}", email, e);
            return new ResponseEntity<>(new AuthResponse("Invalid username or password", null), HttpStatus.UNAUTHORIZED);
        }
    }

    // Bỏ method authenticate
    // private Authentication authenticate(String email, String password) {
    //     try {
    //         Authentication authentication = authenticationManager.authenticate(
    //                 new UsernamePasswordAuthenticationToken(email, password)
    //         );
    //         logger.info("Authentication successful for email: {}", email);
    //         return authentication;
    //     } catch (BadCredentialsException e) {
    //         logger.warn("Authentication failed for email: {}: {}", email, e.getMessage());
    //         throw e;
    //     }
    // }

    @PostMapping("/employee/signup")
    public ResponseEntity<AuthResponse> createEmployeeHandler(@Valid @RequestBody CreateEmployeeRequest employeeRequest) throws Exception {

        String email = employeeRequest.getEmail();
        Employee isEmailExist = employeeService.findByEmail(email);

        if (isEmailExist != null) {
            logger.warn("Attempt to register employee with existing email: {}", email);
            throw new Exception("Email đã được sử dụng với tài khoản khác");
        }

        Employee createdEmployee = new Employee();
        createdEmployee.setEmail(email);
        createdEmployee.setFullName(employeeRequest.getFullName());
        createdEmployee.setPhone(employeeRequest.getPhone());
        // Lấy password từ request body và mã hóa
        createdEmployee.setPasswordHash(passwordEncoder.encode(employeeRequest.getPassword()));
        createdEmployee.setActive(true);

        // Sửa lại: Truyền passwordEncoder vào hàm saveEmployee
        Employee savedEmployee = employeeService.saveEmployee(createdEmployee, passwordEncoder);

        // Bỏ phần này, Spring Security tự xử lý
        // Authentication authentication = new UsernamePasswordAuthenticationToken(savedEmployee.getEmail(), savedEmployee.getPasswordHash());
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        // String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        // authResponse.setJwt(jwt);
        authResponse.setMessage("Đăng ký thành công");

        logger.info("New employee registered: {}", savedEmployee.getEmail());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/employee/login")
    public ResponseEntity<AuthResponse> loginEmployeeHandler(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        logger.debug("Login request: email={}", email);

        // Bỏ phần authenticate thủ công, để Spring Security xử lý
        // Authentication authentication = authenticate(email, loginRequest.getPassword());
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // String jwt = jwtProvider.generateToken(authentication);

        // Thay vào đó, sử dụng AuthenticationManager để xác thực
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse("success", token);
            logger.debug("Login response: {}", authResponse);

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // Xử lý lỗi xác thực
            logger.error("Authentication failed for email: {}", email, e);
            return new ResponseEntity<>(new AuthResponse("Invalid username or password", null), HttpStatus.UNAUTHORIZED);
        }
    }
}