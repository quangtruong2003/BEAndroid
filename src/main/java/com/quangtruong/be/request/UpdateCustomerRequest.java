package com.quangtruong.be.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateCustomerRequest {

    private String fullName;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;

    private String address;

    private LocalDateTime updatedAt = LocalDateTime.now();

    private Boolean isActive; // Use Boolean instead of boolean to allow null values
}