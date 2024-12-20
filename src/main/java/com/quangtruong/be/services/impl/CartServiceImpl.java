package com.quangtruong.be.services.impl;

import com.quangtruong.be.dto.CartDTO;
import com.quangtruong.be.dto.CartItemDTO;
import com.quangtruong.be.entities.Cart;
import com.quangtruong.be.entities.CartItem;
import com.quangtruong.be.entities.Customer;
import com.quangtruong.be.entities.Product;
import com.quangtruong.be.repositories.CartItemRepository;
import com.quangtruong.be.repositories.CartRepository;
import com.quangtruong.be.request.AddCartItemRequest;
import com.quangtruong.be.services.CartService;
import com.quangtruong.be.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Cart getCartByCustomer(Customer customer) {
        Cart cart = cartRepository.findByCustomer(customer);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            cart.setTotalAmount(BigDecimal.ZERO);
            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    @Transactional
    public CartItem addCartItem(Customer customer, AddCartItemRequest request) throws Exception {
        Cart cart = getCartByCustomer(customer);
        Product product = productService.findById(request.getProductId());

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductID().equals(product.getProductID()))
                .findFirst();

        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getUnitPrice()); // Giả sử giá sản phẩm là không đổi
            cartItem.setTotalPrice(BigDecimal.ZERO);
            cartItem = cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
        }

        cartItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cart.setTotalAmount(calculateCartTotal(cart));
        cartRepository.save(cart);
        return cartItem;
    }

    @Override
    @Transactional
    public CartItem updateCartItemQuantity(Customer customer, Long cartItemId, int quantity) throws Exception {
        Cart cart = getCartByCustomer(customer);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("CartItem not found with id: " + cartItemId));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new Exception("CartItem does not belong to the current customer's cart");
        }

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItemRepository.save(cartItem);

        cart.setTotalAmount(calculateCartTotal(cart));
        cartRepository.save(cart);
        return cartItem;
    }

    @Override
    @Transactional
    public void removeCartItem(Customer customer, Long cartItemId) throws Exception {
        Cart cart = getCartByCustomer(customer);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("CartItem not found with id: " + cartItemId));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new Exception("CartItem does not belong to the current customer's cart");
        }

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        cart.setTotalAmount(calculateCartTotal(cart));
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(Customer customer) throws Exception {
        Cart cart = getCartByCustomer(customer);
        cart.getCartItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal calculateCartTotal(Cart cart) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {
            total = total.add(cartItem.getTotalPrice());
        }
        return total;
    }
    @Override
    public CartDTO convertToDto(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setCustomerId(cart.getCustomer().getCustomerId());
        cartDTO.setTotalAmount(cart.getTotalAmount());

        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
                .map(cartItem -> new CartItemDTO(
                        cartItem.getId(),
                        cartItem.getProduct().getProductID(),
                        cartItem.getProduct().getProductName(),
                        cartItem.getQuantity(),
                        cartItem.getPrice(),
                        cartItem.getDiscountedPrice(),
                        cartItem.getTotalPrice(),
                        cartItem.getProduct().getImages().isEmpty() ? null : cartItem.getProduct().getImages().get(0)
                ))
                .collect(Collectors.toList());

        cartDTO.setCartItems(cartItemDTOs);
        return cartDTO;
    }
}