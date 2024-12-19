package com.quangtruong.be.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @Column(name = "RequiredDate")
    private LocalDateTime requiredDate;

    @Column(name = "ShippedDate")
    private LocalDateTime shippedDate;

    @Column(name = "ShipAddress")
    private String shipAddress;

    @Column(name = "ShipCity")
    private String shipCity;

    @Column(name = "ShipPostalCode")
    private String shipPostalCode;

    @Column(name = "ShipCountry")
    private String shipCountry;

    @Column(name = "TotalAmount")
    private BigDecimal totalAmount;

    @Column(name = "PaymentMethod")
    private String paymentMethod;

    @Column(name = "PaymentStatus")
    private String paymentStatus;

    @Column(name = "OrderStatus")
    private String orderStatus;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}