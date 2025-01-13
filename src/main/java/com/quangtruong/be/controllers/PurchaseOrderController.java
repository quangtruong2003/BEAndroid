package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.PurchaseOrderDTO;
import com.quangtruong.be.entities.PurchaseOrder;
import com.quangtruong.be.services.PurchaseOrderService;
import com.quangtruong.be.services.impl.PurchaseOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderServiceImpl purchaseOrderServiceImpl;

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDTO>> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        List<PurchaseOrderDTO> purchaseOrderDTOs = purchaseOrderServiceImpl.toDtoList(purchaseOrders);
        return new ResponseEntity<>(purchaseOrderDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> getPurchaseOrderById(@PathVariable Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(id).orElse(null);
        if(purchaseOrder == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderServiceImpl.convertToDto(purchaseOrder);
        return new ResponseEntity<>(purchaseOrderDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder savedPurchaseOrder = purchaseOrderService.savePurchaseOrder(purchaseOrder);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderServiceImpl.convertToDto(savedPurchaseOrder);
        return new ResponseEntity<>(purchaseOrderDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder purchaseOrder1 = purchaseOrderService.getPurchaseOrderById(id).orElse(null);
        if(purchaseOrder1 == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        purchaseOrder1.setPurchaseOrderId(id);
        purchaseOrderService.savePurchaseOrder(purchaseOrder);
        return new ResponseEntity<>(purchaseOrderServiceImpl.convertToDto(purchaseOrder1), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}