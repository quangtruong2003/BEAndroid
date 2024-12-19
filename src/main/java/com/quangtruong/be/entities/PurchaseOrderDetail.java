package com.quangtruong.be.entities;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchaseorderdetails")
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderDetailId;

    @ManyToOne
    @JoinColumn(name = "PurchaseOrderID")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "UnitCost")
    private BigDecimal unitCost;

    @Column(name = "TotalCost")
    private BigDecimal totalCost;
}
