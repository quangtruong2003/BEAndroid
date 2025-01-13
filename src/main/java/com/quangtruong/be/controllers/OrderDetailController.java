package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.OrderDetailDTO;
import com.quangtruong.be.entities.OrderDetail;
import com.quangtruong.be.services.OrderDetailService;
import com.quangtruong.be.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream().map(orderServiceImpl::convertOrderDetailToDto).toList();
        return new ResponseEntity<>(orderDetailDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(id).orElse(null);
        if(orderDetail == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        OrderDetailDTO orderDetailDTO = orderServiceImpl.convertOrderDetailToDto(orderDetail);
        return new ResponseEntity<>(orderDetailDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        OrderDetail savedOrderDetail = orderDetailService.saveOrderDetail(orderDetail);
        OrderDetailDTO orderDetailDTO = orderServiceImpl.convertOrderDetailToDto(savedOrderDetail);
        return new ResponseEntity<>(orderDetailDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}