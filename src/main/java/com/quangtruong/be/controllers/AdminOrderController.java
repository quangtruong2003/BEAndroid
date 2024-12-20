package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.OrderDTO;
import com.quangtruong.be.entities.Order;
import com.quangtruong.be.services.OrderService;
import com.quangtruong.be.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDTO> orderDTOs = orderServiceImpl.toDtoList(orders);
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) throws Exception {
        Order order = orderService.findById(id);
        OrderDTO orderDTO = orderServiceImpl.convertToDto(order);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}