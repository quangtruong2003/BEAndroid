package com.quangtruong.be.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateEmployeeRequest {
    private String fullName;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    private String phone;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password; // Chỉ dùng khi muốn update password

    private Boolean active;
}