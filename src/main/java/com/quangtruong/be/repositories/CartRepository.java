package com.quangtruong.be.repositories;

import com.quangtruong.be.entities.Cart;
import com.quangtruong.be.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomer(Customer customer);
}