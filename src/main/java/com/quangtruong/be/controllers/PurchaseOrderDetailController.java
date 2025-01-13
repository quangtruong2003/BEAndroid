package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.PurchaseOrderDetailDTO;
import com.quangtruong.be.entities.PurchaseOrderDetail;
import com.quangtruong.be.services.PurchaseOrderDetailService;
import com.quangtruong.be.services.impl.PurchaseOrderServiceImpl;
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

    @Autowired
    private PurchaseOrderServiceImpl purchaseOrderServiceImpl;

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDetailDTO>> getAllPurchaseOrderDetails() {
        List<PurchaseOrderDetail> purchaseOrderDetails = purchaseOrderDetailService.getAllPurchaseOrderDetails();
        List<PurchaseOrderDetailDTO> purchaseOrderDetailDTOs = purchaseOrderDetails.stream().map(purchaseOrderServiceImpl::convertPurchaseOrderDetailToDto).toList();
        return new ResponseEntity<>(purchaseOrderDetailDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDetailDTO> getPurchaseOrderDetailById(@PathVariable Long id) {
        PurchaseOrderDetail purchaseOrderDetail = purchaseOrderDetailService.getPurchaseOrderDetailById(id).orElse(null);
        if(purchaseOrderDetail == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PurchaseOrderDetailDTO purchaseOrderDetailDTO = purchaseOrderServiceImpl.convertPurchaseOrderDetailToDto(purchaseOrderDetail);
        return new ResponseEntity<>(purchaseOrderDetailDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDetailDTO> createPurchaseOrderDetail(@RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        PurchaseOrderDetail savedPurchaseOrderDetail = purchaseOrderDetailService.savePurchaseOrderDetail(purchaseOrderDetail);
        PurchaseOrderDetailDTO purchaseOrderDetailDTO = purchaseOrderServiceImpl.convertPurchaseOrderDetailToDto(savedPurchaseOrderDetail);
        return new ResponseEntity<>(purchaseOrderDetailDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderDetailDTO> updatePurchaseOrderDetail(@PathVariable Long id, @RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        PurchaseOrderDetail purchaseOrderDetail1 = purchaseOrderDetailService.getPurchaseOrderDetailById(id).orElse(null);
        if (purchaseOrderDetail1 == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        purchaseOrderDetail1.setPurchaseOrderDetailId(id);
        purchaseOrderDetailService.savePurchaseOrderDetail(purchaseOrderDetail);
        return new ResponseEntity<>(purchaseOrderServiceImpl.convertPurchaseOrderDetailToDto(purchaseOrderDetail1), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrderDetail(@PathVariable Long id) {
        purchaseOrderDetailService.deletePurchaseOrderDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}