package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.OrderDTO;
import com.quangtruong.be.entities.Order;
import com.quangtruong.be.services.OrderService;
import com.quangtruong.be.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

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
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) throws Exception{
        Order order = orderService.findById(id);
        OrderDTO orderDTO = orderServiceImpl.convertToDto(order);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        OrderDTO orderDTO = orderServiceImpl.convertToDto(savedOrder);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody Order order) throws Exception{
        Order updatedOrder = orderService.findById(id);
        updatedOrder.setOrderId(id);
        orderService.saveOrder(order);
        return new ResponseEntity<>(orderServiceImpl.convertToDto(updatedOrder), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}