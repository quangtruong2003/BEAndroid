package com.quangtruong.be.services;

import com.quangtruong.be.entities.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetails();
    Optional<OrderDetail> getOrderDetailById(Long id);
    OrderDetail saveOrderDetail(OrderDetail orderDetail);
    void deleteOrderDetail(Long id);


}