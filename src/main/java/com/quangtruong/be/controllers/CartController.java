package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.CartDTO;
import com.quangtruong.be.dto.CartItemDTO;
import com.quangtruong.be.entities.Cart;
import com.quangtruong.be.entities.CartItem;
import com.quangtruong.be.entities.Customer;
import com.quangtruong.be.request.AddCartItemRequest;
import com.quangtruong.be.services.CartService;
import com.quangtruong.be.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestHeader("Authorization") String jwt) throws Exception {
        Customer customer = customerService.findUserProfileByJwt(jwt);
        Cart cart = cartService.getCartByCustomer(customer);
        CartDTO cartDTO = cartService.convertToDto(cart);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addCartItem(@RequestHeader("Authorization") String jwt,
                                                   @Valid @RequestBody AddCartItemRequest req) throws Exception {
        Customer customer = customerService.findUserProfileByJwt(jwt);
        CartItem cartItem = cartService.addCartItem(customer, req);
        CartItemDTO cartItemDTO = convertToCartItemDTO(cartItem);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateCartItemQuantity(@RequestHeader("Authorization") String jwt,
                                                              @PathVariable Long cartItemId,
                                                              @RequestParam int quantity) throws Exception {
        Customer customer = customerService.findUserProfileByJwt(jwt);
        CartItem cartItem = cartService.updateCartItemQuantity(customer, cartItemId, quantity);
        CartItemDTO cartItemDTO = convertToCartItemDTO(cartItem);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<CartDTO> removeCartItem(@RequestHeader("Authorization") String jwt,
                                                  @PathVariable Long cartItemId) throws Exception {
        Customer customer = customerService.findUserProfileByJwt(jwt);
        cartService.removeCartItem(customer, cartItemId);
        Cart cart = cartService.getCartByCustomer(customer);
        CartDTO cartDTO = cartService.convertToDto(cart);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartDTO> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        Customer customer = customerService.findUserProfileByJwt(jwt);
        cartService.clearCart(customer);
        Cart cart = cartService.getCartByCustomer(customer);
        CartDTO cartDTO = cartService.convertToDto(cart);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProductId(cartItem.getProduct().getProductID());
        cartItemDTO.setProductName(cartItem.getProduct().getProductName());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setDiscountedPrice(cartItem.getDiscountedPrice());
        cartItemDTO.setTotalPrice(cartItem.getTotalPrice());
        if (!cartItem.getProduct().getImages().isEmpty()) {
            cartItemDTO.setImageUrl(cartItem.getProduct().getImages().get(0));
        }
        return cartItemDTO;
    }
}