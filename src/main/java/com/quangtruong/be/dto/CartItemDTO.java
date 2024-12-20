package com.quangtruong.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountedPrice;
    private BigDecimal totalPrice;
    private String imageUrl;
}