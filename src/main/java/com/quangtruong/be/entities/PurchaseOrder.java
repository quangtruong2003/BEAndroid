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
@Table(name = "purchaseorders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderId;

    @ManyToOne
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;

    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @Column(name = "TotalAmount")
    private BigDecimal totalAmount;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<PurchaseOrderDetail> purchaseOrderDetails;
}
