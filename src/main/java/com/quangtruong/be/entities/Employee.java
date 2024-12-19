package com.quangtruong.be.entities;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "PasswordHash")
    private String passwordHash;

    @Column(name = "Role")
    private String role;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "IsActive")
    private boolean isActive;
}
