package com.quangtruong.be.services.impl;

import com.quangtruong.be.dto.OrderDTO;
import com.quangtruong.be.dto.OrderDetailDTO;
import com.quangtruong.be.entities.Order;
import com.quangtruong.be.entities.OrderDetail;
import com.quangtruong.be.repositories.OrderRepository;
import com.quangtruong.be.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order findById(Long id) throws Exception {
        return orderRepository.findById(id)
                .orElseThrow(() -> new Exception("Order not found with id: " + id));
    }

    public OrderDTO convertToDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setCustomerId(order.getCustomer().getCustomerId());
        dto.setCustomerName(order.getCustomer().getFullName());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setOrderDetails(order.getOrderDetails().stream()
                .map(this::convertOrderDetailToDto)
                .collect(Collectors.toList()));
        return dto;
    }
    public List<OrderDTO> toDtoList(List<Order> orders){
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public OrderDetailDTO convertOrderDetailToDto(OrderDetail orderDetail) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderDetailId(orderDetail.getOrderDetailId());
        dto.setProductId(orderDetail.getProduct().getProductID());
        dto.setProductName(orderDetail.getProduct().getProductName());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setUnitPrice(orderDetail.getUnitPrice());
        dto.setDiscount(orderDetail.getDiscount());
        dto.setTotalPrice(orderDetail.getTotalPrice());
        return dto;
    }
}