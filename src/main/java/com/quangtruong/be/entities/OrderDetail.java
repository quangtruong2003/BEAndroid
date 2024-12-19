package com.quangtruong.be.entities;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderdetails")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "UnitPrice")
    private BigDecimal unitPrice;

    @Column(name = "Discount")
    private BigDecimal discount;

    @Column(name = "TotalPrice")
    private BigDecimal totalPrice;
}
