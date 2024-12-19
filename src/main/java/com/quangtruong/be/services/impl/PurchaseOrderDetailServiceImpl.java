package com.quangtruong.be.services.impl;

import com.quangtruong.be.entities.PurchaseOrderDetail;
import com.quangtruong.be.repositories.PurchaseOrderDetailRepository;
import com.quangtruong.be.services.PurchaseOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderDetailServiceImpl implements PurchaseOrderDetailService {

    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Override
    public List<PurchaseOrderDetail> getAllPurchaseOrderDetails() {
        return purchaseOrderDetailRepository.findAll();
    }

    @Override
    public Optional<PurchaseOrderDetail> getPurchaseOrderDetailById(Long id) {
        return purchaseOrderDetailRepository.findById(id);
    }

    @Override
    public PurchaseOrderDetail savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        return purchaseOrderDetailRepository.save(purchaseOrderDetail);
    }

    @Override
    public void deletePurchaseOrderDetail(Long id) {
        purchaseOrderDetailRepository.deleteById(id);
    }
}