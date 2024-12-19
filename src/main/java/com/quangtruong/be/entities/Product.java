package com.quangtruong.be.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "ProductName")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;

    @Column(name = "UnitPrice")
    private BigDecimal unitPrice;

    @Column(name = "UnitsInStock")
    private int unitsInStock;

    @Column(name = "UnitsOnOrder")
    private int unitsOnOrder;

    @Column(name = "Description")
    private String description;

    @Column(name = "Specifications")
    private String specifications;

    @Column(name = "ImageURL")
    private String imageURL;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "IsActive")
    private boolean isActive;
}