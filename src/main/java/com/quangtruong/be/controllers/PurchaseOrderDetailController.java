package com.quangtruong.be.controllers;

import com.quangtruong.be.entities.PurchaseOrderDetail;
import com.quangtruong.be.services.PurchaseOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-order-details")
public class PurchaseOrderDetailController {

    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDetail>> getAllPurchaseOrderDetails() {
        List<PurchaseOrderDetail> purchaseOrderDetails = purchaseOrderDetailService.getAllPurchaseOrderDetails();
        return new ResponseEntity<>(purchaseOrderDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDetail> getPurchaseOrderDetailById(@PathVariable Long id) {
        return purchaseOrderDetailService.getPurchaseOrderDetailById(id)
                .map(purchaseOrderDetail -> new ResponseEntity<>(purchaseOrderDetail, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDetail> createPurchaseOrderDetail(@RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        PurchaseOrderDetail savedPurchaseOrderDetail = purchaseOrderDetailService.savePurchaseOrderDetail(purchaseOrderDetail);
        return new ResponseEntity<>(savedPurchaseOrderDetail, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderDetail> updatePurchaseOrderDetail(@PathVariable Long id, @RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        return purchaseOrderDetailService.getPurchaseOrderDetailById(id)
                .map(existingPurchaseOrderDetail -> {
                    existingPurchaseOrderDetail.setPurchaseOrderDetailId(id);
                    PurchaseOrderDetail updatedPurchaseOrderDetail = purchaseOrderDetailService.savePurchaseOrderDetail(purchaseOrderDetail);
                    return new ResponseEntity<>(updatedPurchaseOrderDetail, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrderDetail(@PathVariable Long id) {
        purchaseOrderDetailService.deletePurchaseOrderDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}