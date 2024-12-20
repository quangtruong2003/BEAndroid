package com.quangtruong.be.services;

import com.quangtruong.be.dto.CartDTO;
import com.quangtruong.be.entities.Cart;
import com.quangtruong.be.entities.CartItem;
import com.quangtruong.be.entities.Customer;
import com.quangtruong.be.request.AddCartItemRequest;

import java.math.BigDecimal;

public interface CartService {
    Cart getCartByCustomer(Customer customer);
    CartItem addCartItem(Customer customer, AddCartItemRequest request) throws Exception;
    CartItem updateCartItemQuantity(Customer customer, Long cartItemId, int quantity) throws Exception;
    void removeCartItem(Customer customer, Long cartItemId) throws Exception;
    void clearCart(Customer customer) throws Exception;
    BigDecimal calculateCartTotal(Cart cart);

    CartDTO convertToDto(Cart cart);
}