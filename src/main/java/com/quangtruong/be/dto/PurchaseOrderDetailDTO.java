package com.quangtruong.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDetailDTO {
    private Long purchaseOrderDetailId;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
}