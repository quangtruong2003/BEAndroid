package com.quangtruong.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {
    private Long purchaseOrderId;
    private Long supplierId;
    private String supplierName;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private List<PurchaseOrderDetailDTO> purchaseOrderDetails;
}